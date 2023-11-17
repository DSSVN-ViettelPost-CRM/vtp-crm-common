package vtp.crm.common.vo.dto.token;

import lombok.Data;

import java.util.List;

@Data
public class TokenVO {

    private Long userId;

    private String username;

    private String email;

    private String phone;

    private Boolean isActive;

    private List<Long> roleIds;

    private List<Long> permissionIds;

    private Long iat;

    private Long exp;
}
