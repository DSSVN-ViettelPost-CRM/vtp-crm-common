package vtp.crm.common.vo.dto.token;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class CurrentUserInfo {

    private Long userId;

    private String username;

    private String fullName;

    private String email;

    private String phone;

    private Boolean isActive;

    /**
     * An user has multiple working org. this field will be removed in the future. please use #orgIds instead.
     */
    @Deprecated(forRemoval = true)
    private Long orgId;

    private List<Long> orgIds;

    private List<Long> sharedOrgIds;

    private List<RoleInfo> roleInfos;

    private List<PermissionInfo> permissionInfos;

}
