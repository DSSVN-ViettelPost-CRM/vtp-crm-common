package vtp.crm.common.vo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PagedVO {

	@JsonProperty("page")
	private int currentPage;

	private int totalPage;

	private long totalRecord;

	@JsonProperty("size")
	private int pageSize;
}
