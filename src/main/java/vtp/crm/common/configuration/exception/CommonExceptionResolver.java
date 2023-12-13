package vtp.crm.common.configuration.exception;

import feign.FeignException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import vtp.crm.common.utils.Translator;
import vtp.crm.common.utils.common.CommonUtils;

import java.util.Map;
import java.util.Optional;


public class CommonExceptionResolver {

	private static final Logger logger = LogManager.getLogger(CommonExceptionResolver.class);

	private static final String MESSAGE = "message";

	private Map<String, String> logErrorAndBuildResponse(Exception e, String msgCode) {
		String msg = Translator.toLocale(msgCode);
		logger.error(msg, e);
		return Map.of(MESSAGE, msg);
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Map<String, String> handleNoHandlerFound(NoHandlerFoundException e, WebRequest request) {
		return logErrorAndBuildResponse(e, "msg_error_not_found");
	}

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	@ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
	public Map<String, String> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
		return logErrorAndBuildResponse(e, "msg_upload_fail");
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	public Map<String, String> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
		return logErrorAndBuildResponse(e, "msg_error_method_not_allowed");
	}

	@ExceptionHandler({
			MissingServletRequestParameterException.class,
			MethodArgumentTypeMismatchException.class,
			MissingServletRequestPartException.class,
			MultipartException.class,
			HttpMessageNotReadableException.class
	})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, String> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
		return logErrorAndBuildResponse(e, "msg_error_bad_request");
	}

	@ExceptionHandler(MissingRequestHeaderException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public Map<String, String> handleMissingRequestHeaderException(MissingRequestHeaderException e) {
		return logErrorAndBuildResponse(e, "msg_error_unauthorized");
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Map<String, String> handleInternalServerError(Exception e) {
		return logErrorAndBuildResponse(e, "msg_error_server");
	}

	@ExceptionHandler(InvalidInputRequestException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, String> handleInvalidInputRequestError(InvalidInputRequestException e) {
		String message = Translator.toLocale(e.getMessage(), e.getParams());
		logger.error(message, e);
		return Map.of(MESSAGE, message);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		String message = Optional.ofNullable(ex.getFieldError()).map(FieldError::getDefaultMessage)
				.map(Translator::toLocale).orElse(ex.getLocalizedMessage());

		logger.error(message, ex);
		return Map.of(MESSAGE, message);
	}

	@ExceptionHandler(FeignException.class)
	public ResponseEntity<?> handleFeignException(FeignException fe) {
		logger.error(fe);
		HttpStatusCode statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
		try {
			statusCode = HttpStatusCode.valueOf(fe.status());
		} catch (Exception ex) {
			logger.error(ex);
		}
		return new ResponseEntity<>(CommonUtils.convertFromJson(fe.contentUTF8(), Map.class), statusCode);
	}

	@ExceptionHandler(InternalServerException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Map<String, String> handleInternalServerException(InternalServerException ise) {
		String message = Translator.toLocale(ise.getMessage(), ise.getParams());
		logger.error(message, ise);
		return Map.of(MESSAGE, message);
	}

}
