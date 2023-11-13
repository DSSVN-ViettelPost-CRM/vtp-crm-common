package vtp.crm.common.configuration.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import vtp.crm.common.utils.Translator;
import vtp.crm.common.utils.common.ResponseUtil;

@Aspect
public class CommonRestResponseAspect {

    @Pointcut("@annotation(vtp.crm.common.configuration.aspect.CustomRestResponse)")
    public void methodMarkedWithCustomRestResponse() {
    }

    @Pointcut("within(@vtp.crm.common.configuration.aspect.CommonRestResponse *)")
    public void beanAnnotatedWithRestResponse() {
    }

    @Pointcut("execution(public * *(..))")
    public void publicMethod() {
    }

    @Pointcut("publicMethod() && beanAnnotatedWithRestResponse() && !methodMarkedWithCustomRestResponse()")
    public void publicMethodInsideClassMarkedWithCommonRestResponse() {
    }

    @Around(value = "publicMethodInsideClassMarkedWithCommonRestResponse()")
    public Object commonRestResponseAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = proceedingJoinPoint.proceed();
        return ResponseUtil.setResponseData(HttpStatus.OK.value(), result, Translator.toLocale("msg_success"));
    }

}
