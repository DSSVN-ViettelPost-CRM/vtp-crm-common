package vtp.crm.common.utils.common;

import com.google.gson.Gson;

public class CommonUtils {

	private static final Gson gson = new Gson();

	public static <T> T convertFromJson(String json, Class<T> type) {
		return gson.fromJson(json, type);
	}

}
