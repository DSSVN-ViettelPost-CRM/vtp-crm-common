package vtp.crm.common.configuration.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class InvalidInputRequestException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private Integer errorCode;

	private Object[] params;

	public InvalidInputRequestException(String message, Object... params) {
		super(message);
		this.errorCode = HttpStatus.BAD_REQUEST.value();
		this.params = params;
	}
}
