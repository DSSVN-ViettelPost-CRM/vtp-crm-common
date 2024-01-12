package vtp.crm.common.vo.dto.token;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class RoleInfo {

	private Long id;

	private String name;

	private String code;

	private Integer level;

}
