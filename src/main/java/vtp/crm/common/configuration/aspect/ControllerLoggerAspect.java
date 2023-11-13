package vtp.crm.common.configuration.aspect;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

@Aspect
public class ControllerLoggerAspect {

	private static final Logger logger = LoggerFactory.getLogger(ControllerLoggerAspect.class);

	@Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
	public void classAnnotatedWithRestController() {
	}

	@Before("classAnnotatedWithRestController()")
	public void logBeforeRequest(JoinPoint joinPoint) {
		String methodName = joinPoint.getSignature().toShortString();
		Object[] args = joinPoint.getArgs();
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		logger.info("Calling api: request uri= {}", request.getRequestURI());
		logger.info("On method: {} ", methodName);
		logger.info("Passing arguments: {}", Arrays.toString(args));
	}

}
