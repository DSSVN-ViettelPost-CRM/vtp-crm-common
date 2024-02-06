package vtp.crm.common.utils.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Type;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class CommonUtils {

	private static final Gson gson = new GsonBuilder()
			// make it ignore unknown fields in json string
			.setLenient()
			.create();

	public static <T> T convertFromJson(String json, Class<T> type) {
		return gson.fromJson(json, type);
	}

	public static <T> List<T> convertFromJsonToList(String json, Class<T> type) {
		Type listType = TypeToken.getParameterized(List.class, type).getType();
		return gson.fromJson(json, listType);
	}

	public static String convertToJsonString(Object object) {
		return gson.toJson(object);
	}

	public static <T> ResponseEntity<T> buildDownloadFileResponse(String fileName, T content) {
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
				.body(content);
	}

    public static String generateOrgName(String orgName, String orgValue) {
        return orgName != null && !orgName.isBlank()
                ? orgValue + " - " + orgName
                : orgValue;
    }

}
