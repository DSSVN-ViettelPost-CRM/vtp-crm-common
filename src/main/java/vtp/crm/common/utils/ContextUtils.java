package vtp.crm.common.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import vtp.crm.common.vo.dto.token.CurrentUserInfo;

import java.util.Optional;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ContextUtils {

	public static CurrentUserInfo getCurrentUserInfo() {
		String token = getRequestHeader().getAuthorizationToken();
		return TokenUtils.decodeToken(token, CurrentUserInfo.class);
	}	

	public static RequestHeaderHolder getRequestHeader() {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

		HttpServletRequest requestHeader = null;
		if (attributes != null) {
			requestHeader = attributes.getRequest();
		}
		return new RequestHeaderHolder(requestHeader);
	}

	public static class RequestHeaderHolder {

		private final Optional<HttpServletRequest> requestHeaderOpt;


		RequestHeaderHolder(HttpServletRequest requestHeader) {
			this.requestHeaderOpt = Optional.ofNullable(requestHeader);
		}

		public String getAttribute(String key) {
			return requestHeaderOpt
					.map(header -> header.getHeader(key))
					.orElse(null);
		}

		public String getAuthorizationToken() {
			return this.getAttribute(HttpHeaders.AUTHORIZATION);
		}

	}

}
