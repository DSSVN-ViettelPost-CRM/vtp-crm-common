package vtp.crm.common.utils.notification;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.SerializationUtils;
import vtp.crm.common.utils.common.CommonUtils;
import vtp.crm.common.vo.notication.FcmTokensByUsersResponse;
import vtp.crm.common.vo.notication.NotificationTypeVO;
import vtp.crm.common.vo.notication.NotifyMessageDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NotificationUtils {


    /**
     * gen notification
     *
     * @param receivers            ds nguoi nhan notification. call api /get-fcm-tokens-by-users
     * @param notificationTemplate notification template. call api notification-type/type
     * @param dataHolder           dat ten bien trung voi ten trong template. vi du {{tenBien}}
     * @param <D>                  type cua dataholder
     */
    public static <D> List<NotifyMessageDTO> formatNotifications(
            List<FcmTokensByUsersResponse> receivers,
            NotificationTypeVO notificationTemplate,
            D dataHolder) {

        // format notification content
        Map<String, Object> dataMap = CommonUtils.convertObjectToHashMap(dataHolder);
        String title = formatContent(notificationTemplate.getName(), dataMap);
        String body = formatContent(notificationTemplate.getValue(), dataMap);

        List<NotifyMessageDTO> notifications = new ArrayList<>();
        for (FcmTokensByUsersResponse receiver : receivers) {
            if (ObjectUtils.isNotEmpty(receiver.getFcmTokens())) {
                for (String token : receiver.getFcmTokens()) {
                    notifications.add(
                            NotifyMessageDTO.builder()
                                    .userId(receiver.getUserId())
                                    .isInternal(receiver.getIsInternal())
                                    .phone(receiver.getPhone())
                                    .fcmToken(token)
                                    .notificationTypeId(notificationTemplate.getAdNotifycationTypeId())
                                    .title(title)
                                    .body(body)
                                    .build()
                    );
                }
            }
        }
        return notifications;
    }


    /**
     * gen notification
     *
     * @param receivers              ds nguoi nhan notification. call api /get-fcm-tokens-by-users
     * @param notificationTemplate   notification template. call api notification-type/type
     * @param dataHolder             dat ten bien trung voi ten trong template. vi du {{tenBien}}
     * @param dataHolderByReceiverFn function cho phep set dataholder (cloned) voi moi user nguoi nhan
     * @param <D>                    type cua dataholder
     */
    public static <D extends Serializable> List<NotifyMessageDTO> formatNotifications(
            List<FcmTokensByUsersResponse> receivers,
            NotificationTypeVO notificationTemplate,
            D dataHolder,
            BiConsumer<D, FcmTokensByUsersResponse> dataHolderByReceiverFn) {

        List<NotifyMessageDTO> notifications = new ArrayList<>();
        for (FcmTokensByUsersResponse receiver : receivers) {

            // deep copy de khong anh huong toi data ban dau
            D dataHolderClone = SerializationUtils.clone(dataHolder);
            dataHolderByReceiverFn.accept(dataHolderClone, receiver);

            // format notification content
            Map<String, Object> dataMap = CommonUtils.convertObjectToHashMap(dataHolderClone);
            String title = formatContent(notificationTemplate.getName(), dataMap);
            String body = formatContent(notificationTemplate.getValue(), dataMap);

            if (ObjectUtils.isNotEmpty(receiver.getFcmTokens())) {
                for (String token : receiver.getFcmTokens()) {
                    notifications.add(
                            NotifyMessageDTO.builder()
                                    .userId(receiver.getUserId())
                                    .isInternal(receiver.getIsInternal())
                                    .phone(receiver.getPhone())
                                    .fcmToken(token)
                                    .notificationTypeId(notificationTemplate.getAdNotifycationTypeId())
                                    .title(title)
                                    .body(body)
                                    .build()
                    );
                }
            }
        }
        return notifications;
    }

    private static String formatContent(String content, Map<String, Object> dataHolder) {
        if (ObjectUtils.isEmpty(content)) {
            return content;
        }
        for (var entry : dataHolder.entrySet()) {
            content = content.replaceAll(generateKeyPlaceHolder(entry.getKey()), String.valueOf(entry.getValue()));
        }
        return content;
    }

    private static String generateKeyPlaceHolder(String key) {
        return "\\{\\{" + key + "\\}\\}";
    }

}
