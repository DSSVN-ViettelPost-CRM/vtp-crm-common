package vtp.crm.common.utils.common;

import com.google.gson.Gson;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

public class CommonUtils {

	private static final Gson gson = new Gson();

	public static <T> T convertFromJson(String json, Class<T> type) {
		return gson.fromJson(json, type);
	}

	public static <T> ResponseEntity<T> buildDownloadFileResponse(String fileName, T content) {
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
				.body(content);
	}

}
