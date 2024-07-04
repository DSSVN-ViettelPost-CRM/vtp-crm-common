package vtp.crm.common.vo.notication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class NotifyMessage {

    private List<String> fcmTokens = new ArrayList<>();

    private Long userId;        //Người nhận được thông báo - Quan hệ với bảng ad_user

    private Boolean isInternal;     //true: user noi bo, false: user CRM

    private String title;

    private String body;

    private Long notificationTypeId;    //Xác định thuộc loại thông báo nào

    private Long orgId;     //Đơn vị nhận thông báo

    private String phone;

    private Long accountId;

    private Long customerId;

    private Long campaignId;

    private String notificationValue;

    private String notificationTemplate;
}
