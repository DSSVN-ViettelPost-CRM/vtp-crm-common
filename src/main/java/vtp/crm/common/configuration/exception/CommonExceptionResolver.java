package vtp.crm.common.configuration.exception;

import java.nio.file.AccessDeniedException;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import feign.FeignException;
import vtp.crm.common.utils.Translator;
import vtp.crm.common.utils.common.CommonUtils;
import vtp.crm.common.vo.response.ErrorResponse;

public class CommonExceptionResolver {

	private static final Logger logger = LogManager.getLogger(CommonExceptionResolver.class);

	@Value("${spring.application.name}")
	private String serviceName;

	protected ErrorResponse logErrorAndBuildResponse(Exception e, String msgCode, Object... params) {
		String msg = Translator.toLocale(msgCode, params);
		logger.error(msg, e);
		return new ErrorResponse().setMessage(msg).setService(serviceName).setDetailError(e.toString());
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorResponse handleNoHandlerFound(NoHandlerFoundException e) {
		return logErrorAndBuildResponse(e, "msg_error_not_found");
	}

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	@ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
	public ErrorResponse handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
		return logErrorAndBuildResponse(e, "msg_upload_fail");
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	public ErrorResponse handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
		return logErrorAndBuildResponse(e, "msg_error_method_not_allowed");
	}

	@ExceptionHandler({ MissingServletRequestParameterException.class, MethodArgumentTypeMismatchException.class,
			MissingServletRequestPartException.class, MultipartException.class, HttpMessageNotReadableException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleMissingServletRequestParameterException(Exception e) {
		return logErrorAndBuildResponse(e, "msg_error_bad_request");
	}

	@ExceptionHandler(MissingRequestHeaderException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ErrorResponse handleMissingRequestHeaderException(MissingRequestHeaderException e) {
		return logErrorAndBuildResponse(e, "msg_error_unauthorized");
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorResponse handleInternalServerError(Exception e) {
		return logErrorAndBuildResponse(e, "msg_error_server");
	}

	@ExceptionHandler(InvalidInputRequestException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleInvalidInputRequestError(InvalidInputRequestException e) {
		return logErrorAndBuildResponse(e, e.getMessage(), e.getParams());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ErrorResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
		String message = Optional.ofNullable(ex.getFieldError()).map(FieldError::getDefaultMessage)
				.orElse(ex.getLocalizedMessage());
		return logErrorAndBuildResponse(ex, message);
	}

	@ExceptionHandler(FeignException.class)
	public ResponseEntity<?> handleFeignException(FeignException fe) {
		logger.error(fe);
		HttpStatusCode statusCode = HttpStatus.INTERNAL_SERVER_ERROR;

		String message = Optional.ofNullable(fe.contentUTF8())
				.map(content -> CommonUtils.convertFromJson(content, ErrorResponse.class))
				.map(ErrorResponse::getMessage).orElse(fe.getMessage());

		try {
			statusCode = HttpStatusCode.valueOf(fe.status());
		} catch (Exception ignored) {
		}
		ErrorResponse errorResponse = ErrorResponse.builder().message(message).service(serviceName)
				.detailError(fe.getMessage()).build();
		return new ResponseEntity<>(errorResponse, statusCode);
	}

	@ExceptionHandler(InternalServerException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorResponse handleInternalServerException(InternalServerException ise) {
		return logErrorAndBuildResponse(ise, ise.getMessage(), ise.getParams());
	}

	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ErrorResponse handleAccessDeniedException(AccessDeniedException e) {
		return logErrorAndBuildResponse(e, "msg_error_not_permission");
	}

	@ExceptionHandler(CustomException.class)
	public ErrorResponse handleCustomException(CustomException e) {
		return logErrorAndBuildResponse(e, e.getMessage(), e.getParams());
	}
}
