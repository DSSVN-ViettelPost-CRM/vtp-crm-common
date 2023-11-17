package vtp.crm.common.utils;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import vtp.crm.common.configuration.exception.InvalidInputRequestException;
import vtp.crm.common.vo.dto.token.CurrentUserInfo;

import java.util.Optional;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ContextUtils {

	public static CurrentUserInfo getCurrentUserInfo() {
		String token = getRequestHeader().getAuthorizationToken();
		return decodeToken(token);
	}

	public static CurrentUserInfo decodeToken(String token) {
		if (ObjectUtils.isEmpty(token)) {
			return null;
		}
		try {
			Base64 base64 = new Base64(true);
			String jwtToken = token.substring(7);
			String[] split_string = jwtToken.split("\\.");
			String tokenPayload = new String(base64.decode(split_string[1]));
			Gson gson = new Gson();
			return gson.fromJson(tokenPayload, CurrentUserInfo.class);
		} catch (Exception e) {
			throw new InvalidInputRequestException("msg_sso_validate_token_failed");
		}
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
