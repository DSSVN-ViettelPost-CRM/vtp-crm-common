package vtp.crm.common.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import vtp.crm.common.utils.common.CommonUtils;
import vtp.crm.common.vo.dto.token.CurrentUserInfo;

import java.util.List;
import java.util.Optional;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ContextUtils {

	public static CurrentUserInfo getCurrentUserInfo() {

		// note: userId always decoded from token
		RequestHeaderWrapper requestHeader = getRequestHeader();
		return Optional.ofNullable(requestHeader.getAuthorizationToken())
				.map(token -> TokenUtils.decodeToken(token, CurrentUserInfo.class))
				.map(currentUser -> currentUser
						.setOrgId(requestHeader.getOrgId().orElse(null))
						.setOrgIds(requestHeader.getOrgIds())
						.setSharedOrgIds(requestHeader.getSharedOrgIds())
				)
				.orElse(null);
	}

	public static RequestHeaderWrapper getRequestHeader() {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

		HttpServletRequest requestHeader = null;
		if (attributes != null) {
			requestHeader = attributes.getRequest();
		}
		return new RequestHeaderWrapper(requestHeader);
	}

	public static class RequestHeaderWrapper {

		private final Optional<HttpServletRequest> requestHeaderOpt;


		RequestHeaderWrapper(HttpServletRequest requestHeader) {
			this.requestHeaderOpt = Optional.ofNullable(requestHeader);
		}

		public Optional<String> getAttribute(String key) {
			return requestHeaderOpt
					.map(header -> header.getHeader(key));
		}

		public String getAuthorizationToken() {
			return this.getAttribute(HttpHeaders.AUTHORIZATION).orElse(null);
		}

		public Optional<Long> getUserId() {
			return this.getAttribute("X-USER-ID")
					.map(StringUtils::trimToNull)
					.map(Long::valueOf);
		}

		/**
		 * An user has multiple working orgs. Use #getOrgIds() instead
		 */
		@Deprecated(forRemoval = true)
		public Optional<Long> getOrgId() {
			return this.getAttribute("X-ORG-ID")
					.map(StringUtils::trimToNull)
					.map(Long::valueOf);
		}

		public List<Long> getOrgIds() {
			return this.getAttribute("X-ORG-IDS")
					.map(StringUtils::trimToNull)
					.map(orgIdsStr -> CommonUtils.convertFromJsonToList(orgIdsStr, Long.class))
					.orElse(List.of());
		}

		public List<Long> getSharedOrgIds() {
			return this.getAttribute("X-SHARED-ORG-IDS")
					.map(StringUtils::trimToNull)
					.map(orgIdsStr -> CommonUtils.convertFromJsonToList(orgIdsStr, Long.class))
					.orElse(List.of());
		}

	}

}
