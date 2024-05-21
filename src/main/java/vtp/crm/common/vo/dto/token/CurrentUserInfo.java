package vtp.crm.common.vo.dto.token;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import one.util.streamex.StreamEx;
import org.apache.commons.lang3.ObjectUtils;

import java.util.*;

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

    public boolean hasPermission(String permissionCode) {
        return ObjectUtils.isEmpty(permissionInfos) || ObjectUtils.isEmpty(permissionCode)
                ? false
                : StreamEx.of(permissionInfos).anyMatch(permissionInfo -> Objects.equals(permissionInfo.getCode(), permissionCode));
    }

    public boolean hasAllPermissions(Collection<String> permissions) {
        if (ObjectUtils.isEmpty(permissions) || ObjectUtils.isEmpty(permissions)) {
            return false;
        }
        Set<String> permissionsSet = new HashSet<>(permissions);
        return StreamEx.of(permissionInfos)
                .allMatch(permissionInfo -> permissionsSet.contains(permissionInfo.getCode()));
    }

    public boolean hasAnyPermissions(Collection<String> permissions) {
        if (ObjectUtils.isEmpty(permissions) || ObjectUtils.isEmpty(permissions)) {
            return false;
        }
        Set<String> permissionsSet = new HashSet<>(permissions);
        return StreamEx.of(permissionInfos)
                .anyMatch(permissionInfo -> permissionsSet.contains(permissionInfo.getCode()));
    }

}
