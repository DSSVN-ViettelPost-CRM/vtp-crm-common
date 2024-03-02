package vtp.crm.common.vo;

import lombok.Getter;

@Getter
public enum CrmHeaders {

	AUTHORIZATION("Authorization"),

	USER_ID("X-USER-ID"),

	ORG_ID("X-ORG-ID"),

	ORG_IDS("X-ORG-IDS"),

	SHARED_ORG_IDS("X-SHARED-ORG-IDS");

	private final String value;

	CrmHeaders(String value) {
		this.value = value;
	}

}
