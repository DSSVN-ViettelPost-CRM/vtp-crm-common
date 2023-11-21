package vtp.crm.common.vo.dto.token;

import lombok.Data;

import java.util.List;

@Data
public class CurrentUserInfo {

    private Long userId;

    private String username;

    private String email;

    private String phone;

    private Boolean isActive;

    private Long orgId;

    private List<RoleInfo> roles;

    private List<Long> permissionIds;

    private Long iat;

    private Long exp;
}
