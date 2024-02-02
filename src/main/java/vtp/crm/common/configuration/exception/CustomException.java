package vtp.crm.common.configuration.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private Integer errorCode;

	private Object[] params;

	public CustomException(String message, Object... params) {
		super(message);
		this.errorCode = HttpStatus.BAD_REQUEST.value();
		this.params = params;
	}

	public CustomException(Integer errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}
}
