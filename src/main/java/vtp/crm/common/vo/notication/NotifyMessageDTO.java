package vtp.crm.common.vo.notication;

import lombok.Data;

@Data
public class NotifyMessageDTO {

    private String fcmToken;

    private Long userId;        //Người nhận được thông báo - Quan hệ với bảng ad_user

    private Boolean isInternal;     //true: user noi bo, false: user CRM

    private String title;

    private String body;

    private Long notificationTypeId;    //Xác định thuộc loại thông báo nào

    private Long orgId;     //Đơn vị nhận thông báo

    private String phone;
}
