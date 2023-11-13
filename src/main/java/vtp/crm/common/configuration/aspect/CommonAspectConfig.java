package vtp.crm.common.configuration.aspect;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class CommonAspectConfig {

	@Bean
	public CommonRestResponseAspect commonRestResponseAspect() {
		return new CommonRestResponseAspect();
	}

	@Bean
	public ControllerLoggerAspect controllerLoggerAspect() {
		return new ControllerLoggerAspect();
	}

}
