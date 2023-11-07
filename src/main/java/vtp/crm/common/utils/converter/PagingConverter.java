package vtp.crm.common.utils.converter;

import jakarta.annotation.Nullable;
import one.util.streamex.StreamEx;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import vtp.crm.common.utils.Constants;
import vtp.crm.common.vo.request.*;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class PagingConverter {

	private PagingConverter() {

	}

	public static Pageable toPageable(@Nullable SortingAndPagingRequestVO request, @Nullable Collection<SortingRequest> defaultSortings) {
		PagingRequest standardizedPaging = standardizePaging(request);
		List<Sort.Order> orders = standardizeSorting(request, defaultSortings).stream()
				.map(sorting -> new Sort.Order(Sort.Direction.fromString(sorting.getSortDir()), sorting.getSortKey()))
				.toList();
		return PageRequest.of(
				standardizedPaging.getPage(),
				standardizedPaging.getSize(),
				Sort.by(orders)
		);
	}

	private static PagingRequest standardizePaging(PagingRequest pagingRequest) {
		if (pagingRequest == null || pagingRequest.getPage() < 0) {
			return PagingRequestVO.builder()
					.page(0)
					.size(Integer.MAX_VALUE)
					.build();
		}
		int page = pagingRequest.getPage() > 0 ? pagingRequest.getPage() - 1 : pagingRequest.getPage();
		int pageSize = pagingRequest.getSize() > 0 ? pagingRequest.getSize() : Constants.DEFAULT_SIZE;
		return PagingRequestVO.builder()
				.page(page)
				.size(pageSize)
				.build();
	}

	private static List<SortingRequest> standardizeSorting(SortingRequest sortingRequest, Collection<SortingRequest> defaultSortings) {
		List<SortingRequest> standardizedSorting = new ArrayList<>();
		if (sortingRequest != null && StringUtils.isNotEmpty(sortingRequest.getSortKey())) {
			standardizedSorting.add(standardizeSorting(sortingRequest));
		} else if (ObjectUtils.isNotEmpty(defaultSortings)) {
			List<SortingRequest> defaultSort = StreamEx.of(defaultSortings).map(PagingConverter::standardizeSorting).toList();
			standardizedSorting.addAll(defaultSort);
		}
		return StreamEx.of(standardizedSorting).nonNull().toList();
	}

	private static SortingRequest standardizeSorting(SortingRequest sortingRequest) {
		if (StringUtils.isEmpty(sortingRequest.getSortKey())) {
			return null;
		}
		String sortKey = sortingRequest.getSortKey();
		String sortDir = StringUtils.isNotEmpty(sortingRequest.getSortDir()) ? sortingRequest.getSortDir() : Constants.PAGE_SORT_ASC;
		return SortingRequestVO.builder().sortKey(sortKey).sortDir(sortDir).build();
	}

}
