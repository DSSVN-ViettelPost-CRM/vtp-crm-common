package vtp.crm.common.vo.notication;

import lombok.Data;

import java.util.List;

@Data
public class FcmTokensByUsersResponse {

    private Long userId;

    private Boolean isInternal;

    private String phone;

    private List<Long> orgIds;

    private List<String> fcmTokens;
}
