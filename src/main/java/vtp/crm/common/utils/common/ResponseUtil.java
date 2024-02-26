package vtp.crm.common.utils.common;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import vtp.crm.common.utils.Constants;
import vtp.crm.common.utils.Translator;
import vtp.crm.common.utils.converter.PagingConverter;
import vtp.crm.common.vo.BuildableTreeVO;
import vtp.crm.common.vo.request.SortingAndPagingRequestVO;
import vtp.crm.common.vo.request.SortingRequest;
import vtp.crm.common.vo.request.SortingRequestVO;
import vtp.crm.common.vo.response.PagedResult;
import vtp.crm.common.vo.response.PagedVO;

import java.util.*;
import java.util.function.Function;

public final class ResponseUtil {
	private ResponseUtil() {
	}

	// private static final Logger logger =
	// LogManager.getLogger(ResponseUtil.class);

	public static ResponseEntity<Map<String, Object>> setResponseData(Integer statusCodeAsInt, Object data,
																	  String message) {
		Map<String, Object> responseData = new HashMap<>();

		String responseMessage = null;
		if (message != null) {
			responseMessage = message;
		} else {
			responseMessage = getResponseMessage(statusCodeAsInt, false);
		}
		responseData.put("message", responseMessage);

		if (statusCodeAsInt == null || HttpStatus.resolve(statusCodeAsInt) == null
				|| HttpStatus.resolve(statusCodeAsInt).is5xxServerError()) {
			return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		HttpStatus statusCode = HttpStatus.resolve(statusCodeAsInt);
		if (statusCode.is2xxSuccessful() && data != null) {
			responseData.put("data", data);
		}

		return new ResponseEntity<>(responseData, statusCode);
	}

	public static ResponseEntity<Map<String, Object>> setLogonResponseData(Integer statusCodeAsInt, Object data,
																		   String message) {
		Map<String, Object> responseData = new HashMap<>();

		String responseMessage = null;
		if (message != null) {
			responseMessage = message;
		} else {
			responseMessage = getResponseMessage(statusCodeAsInt, true);
		}
		responseData.put("message", responseMessage);

		if (statusCodeAsInt == null || HttpStatus.resolve(statusCodeAsInt) == null
				|| HttpStatus.resolve(statusCodeAsInt).is5xxServerError()) {
			return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		HttpStatus statusCode = HttpStatus.resolve(statusCodeAsInt);
		if (statusCode.is2xxSuccessful() && data != null) {
			responseData.put("data", data);
		}

		return new ResponseEntity<>(responseData, statusCode);
	}

	public static ResponseEntity<Map<String, Object>> setEmptyDataResponse(Integer statusCodeAsInt) {
		return setResponseData(statusCodeAsInt, null, null);
	}

	public static ResponseEntity<Map<String, Object>> setResponseInvalidData(String message) {
		Map<String, Object> responseData = new HashMap<>();
		responseData.put("message", message);

		return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
	}

	private static String getResponseMessage(Integer statusCodeAsInt, boolean isLogon) {
		if (statusCodeAsInt == null || HttpStatus.resolve(statusCodeAsInt) == null
				|| HttpStatus.resolve(statusCodeAsInt).is5xxServerError()) {
			return Translator.toLocale("msg_error_server");
		}

		HttpStatus statusCode = HttpStatus.resolve(statusCodeAsInt);
		if (statusCode.is2xxSuccessful()) {
			return Translator.toLocale("msg_success");
		}

		if (statusCode.is4xxClientError()) {
			if (statusCode.equals(HttpStatus.UNAUTHORIZED)) {
				return isLogon ? Translator.toLocale("msg_error_login_fail")
						: Translator.toLocale("msg_error_unauthorized");
			}

			if (statusCode.equals(HttpStatus.FORBIDDEN)) {
				return Translator.toLocale("msg_forbidden");
			}

			if (statusCode.equals(HttpStatus.CONFLICT)) {
				return Translator.toLocale("msg_error_conflict");
			}

			return Translator.toLocale("msg_error_wagby_server") + statusCode.value();
		}

		return Translator.toLocale("msg_error_unknown");
	}

	public static boolean isSuccessResponse(Integer statusCode) {
		if (statusCode == null) {
			return false;
		}

		return statusCode >= 200 && statusCode < 300;
	}

	public static PagedVO convertPageDataToPagedVO(Page<?> page) {
		PagedVO pagedVO = new PagedVO();

		// set PagedVO
		pagedVO.setTotalPage(page.getTotalPages());
		pagedVO.setPageSize(page.getSize());
		pagedVO.setTotalRecord(page.getTotalElements());
		pagedVO.setCurrentPage(
				page.getPageable().isUnpaged() ? 0 : page.getPageable().getPageNumber() + Constants.PAGE_NUM);
		return pagedVO;
	}

	public static <R, U> List<R> convertQueriedDataToResponseDto(List<U> data,
																 Function<Optional<U>, R> dataToResponseMapper) {
		if (data != null && !data.isEmpty()) {
			return data.stream().map(Optional::ofNullable).map(dataToResponseMapper).toList();
		}
		return List.of();
	}

	public static <R, U> PagedResult<R> commonPaging(SortingAndPagingRequestVO pagingRequestVO,
													 String defaultSortKey,
													 Function<Pageable, Page<U>> jpaQueryMethod,
													 Function<List<U>, List<R>> dataToResponseMapper) {

		List<SortingRequest> defaultSorting = List.of(SortingRequestVO.builder().sortKey(defaultSortKey).build());
		return commonPaging(pagingRequestVO, defaultSorting, jpaQueryMethod, dataToResponseMapper);
	}

	public static <R, U> PagedResult<R> commonPaging(SortingAndPagingRequestVO pagingRequestVO,
													 Collection<SortingRequest> defaultSorting,
													 Function<Pageable, Page<U>> jpaQueryMethod,
													 Function<List<U>, List<R>> dataToResponseMapper) {

		Pageable pageable = PagingConverter.toPageable(pagingRequestVO, defaultSorting);
		// Get data pageable
		Page<U> page = jpaQueryMethod.apply(pageable);

		return PagedResult.<R>builder()
				.paging(convertPageDataToPagedVO(page))
				.elements(dataToResponseMapper.apply(page.getContent()))
				.build();
	}
	/**
	 * Build a tree from a data set. The data set must contain at least one data
	 * that has null parentId to be a root, or else it will return an empty tree
	 *
	 * @param data the data to build a tree
	 * @return a tree with a list of roots
	 * @param <ID> type of the ID
	 * @param <N>  type of the node
	 */
	public static <ID, N extends BuildableTreeVO<ID>> List<N> buildTreeData(List<N> data) {
		if (ObjectUtils.isEmpty(data)) {
			return new ArrayList<>();
		}
		// linked hashmap to keep the order of data
		Map<ID, N> dataMappedById = new LinkedHashMap<>();
		for (N node : data) {
			dataMappedById.put(node.getId(), node);
			// init children list
			if (node.getChildren() == null) {
				node.setChildren(new ArrayList<>());
			}
		}
		List<N> roots = new ArrayList<>();
		for (Map.Entry<ID, N> entry : dataMappedById.entrySet()) {
			N currentNode = entry.getValue();
			N parentNode = dataMappedById.get(currentNode.getParentId());

			if (parentNode == null) {
				// if current node is a root node -> add to roots list and skip this step
				roots.add(currentNode);
			} else {
				// if current node is not a root -> add to children list of the parent
				parentNode.getChildren().add(currentNode);
			}

		}
		return roots;
	}

    public static <T> PagedResult<T> emptyPagedResult() {
        return emptyPagedResult(null);
    }

    public static <T> PagedResult<T> emptyPagedResult(SortingAndPagingRequestVO pagingRequest) {
        int pageSize = pagingRequest != null ? pagingRequest.getSize() : Constants.DEFAULT_SIZE;
        PagedVO pagedVO = new PagedVO()
                .setTotalPage(0)
                .setTotalRecord(0)
                .setCurrentPage(1)
                .setPageSize(pageSize);
        return new PagedResult<>(pagedVO, List.of());
    }

}
