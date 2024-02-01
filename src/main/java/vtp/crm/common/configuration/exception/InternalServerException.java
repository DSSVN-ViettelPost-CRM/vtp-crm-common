package vtp.crm.common.configuration.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InternalServerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private Object[] params;

	public InternalServerException(String message, Object... params) {
		super(message);
		this.params = params;
	}

}
