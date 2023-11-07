package vtp.crm.common.utils.common;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import vtp.crm.common.utils.Constants;

public class SMSUtils {

	public void sendSMS(String content) {

		// set header
		HttpHeaders headers = new HttpHeaders();
		headers.setBasicAuth(Constants.SMS_AUTH);
		headers.setContentType(MediaType.APPLICATION_JSON);

		// set content
		HttpEntity<String> entity = new HttpEntity<String>(content, headers);

		// call api
		RestTemplate restTemp = new RestTemplate();
		restTemp.exchange(Constants.SMS_URI, HttpMethod.POST, entity, String.class);
	}
}
