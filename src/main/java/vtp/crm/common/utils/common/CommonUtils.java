package vtp.crm.common.utils.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import one.util.streamex.StreamEx;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    public static Map<String, Object> convertObjectToHashMap(Object obj) {
        return obj == null
                ? Map.of()
                : gson.fromJson(convertToJsonString(obj), new TypeToken<Map<String, Object>>(){}.getType());
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

    public static String combineUserCodeAndName(String userCode, String fullName, String separatorString) {
        return StringUtils.isNotEmpty(userCode)
                ? userCode + separatorString + fullName
                : fullName;
    }

    public static String combineUserCodeAndName(String userCode, String fullName) {
        return combineUserCodeAndName(userCode, fullName, "-");
    }

    public static boolean isAnyEmpty(Object... objs) {
        if (ObjectUtils.isEmpty(objs)) {
            return false;
        }
        return StreamEx.of(objs).anyMatch(ObjectUtils::isEmpty);
    }

	public static String censorPhone(String origPhone) {
		return Optional.ofNullable(origPhone)
				.map(StringUtils::trimToNull)
				.map(phone -> phone.length() >= 3 ? phone.substring(0, phone.length() - 3) + "***" : phone.replaceAll(".*", "*"))
				.orElse(null);
	}

	public static String censorAddress(String districtName, String provinceName) {
		return StreamEx.of(new String[]{"***", districtName, provinceName}).map(StringUtils::trimToNull).nonNull().joining(", ");
	}

}
