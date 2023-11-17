package vtp.crm.common.utils;

import com.google.gson.Gson;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import vtp.crm.common.configuration.exception.InvalidInputRequestException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenUtils {

	public static <T> T decodeToken(String token, Class<T> clazz) {
		if (ObjectUtils.isEmpty(token)) {
			return null;
		}
		try {
			Base64 base64 = new Base64(true);
			String jwtToken = token.substring(7);
			String[] split_string = jwtToken.split("\\.");
			String tokenPayload = new String(base64.decode(split_string[1]));
			Gson gson = new Gson();
			return gson.fromJson(tokenPayload, clazz);
		} catch (Exception e) {
			throw new InvalidInputRequestException("msg_sso_validate_token_failed");
		}
	}

}
