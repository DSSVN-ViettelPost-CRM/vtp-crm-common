package vtp.crm.common.utils;

import com.google.gson.Gson;
import org.apache.tomcat.util.codec.binary.Base64;
import vtp.crm.common.configuration.exception.InvalidInputRequestException;
import vtp.crm.common.vo.dto.token.TokenVO;

public class TokenUtils {

    public static TokenVO decodeToken(String token) {
        try {
            Base64 base64 = new Base64(true);
            String jwtToken = token.substring(7);
            String[] split_string = jwtToken.split("\\.");
            String tokenPayload = new String(base64.decode(split_string[1]));
            Gson gson = new Gson();
            return gson.fromJson(tokenPayload, TokenVO.class);
        } catch (Exception e) {
            throw new InvalidInputRequestException("msg_sso_validate_token_failed");
        }
    }
}
