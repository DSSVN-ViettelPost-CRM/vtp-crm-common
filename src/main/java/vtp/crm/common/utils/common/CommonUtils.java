package vtp.crm.common.utils.common;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class CommonUtils {

	private static final ObjectMapper objectMapper = JsonMapper.builder()
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
			.enable(SerializationFeature.INDENT_OUTPUT)
			.addModule(new JavaTimeModule())
			.build();

	public static <T> T convertFromJson(String json, Class<T> type) {
		return objectMapper.convertValue(json, type);
	}

	public static String convertToJsonString(Object object) {
		if (object == null) {
			return null;
		}
		try {
			return objectMapper.writeValueAsString(object);
		} catch (Exception e) {
			log.error("[Error] An error has occurred while trying to convert an object to json", e);
			throw new RuntimeException("Internal server error");
		}
	}

	public static <T> ResponseEntity<T> buildDownloadFileResponse(String fileName, T content) {
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
				.body(content);
	}

}
