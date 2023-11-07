package vtp.crm.common.vo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SortingAndPagingRequestVO implements PagingRequest, SortingRequest {

	private int page;

	private int size;

	private String sortKey;

	private String sortDir;

}
