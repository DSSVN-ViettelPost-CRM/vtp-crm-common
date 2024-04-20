package vtp.crm.common.vo.notication;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationTypeVO {

	private Long adNotifycationTypeId;

	private String value;

	private String name;

	private Integer countValue;

	private String notifyTypeTime;

	private String notifyType;

	private Boolean isActive;

	private Date updated;

	private String notifyNote;

}
