package vtp.crm.common.configuration.feign;

import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import one.util.streamex.StreamEx;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import vtp.crm.common.vo.CrmHeaders;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class FeignForwardHeaderConfig extends CommonFeignConfiguration {

	private final Set<String> allowedHeadersForward;


	public FeignForwardHeaderConfig() {
		this.allowedHeadersForward = StreamEx.of(CrmHeaders.values())
				.map(CrmHeaders::getValue)
				.map(this::standardizeHeader)
				.toSet();
	}

	private String standardizeHeader(String header) {
		return StringUtils.toRootLowerCase(header);
	}

	@Bean
	public RequestInterceptor requestInterceptor() {
		return template -> {
			HttpServletRequest request = Optional.ofNullable(RequestContextHolder.getRequestAttributes())
					.map(r -> (ServletRequestAttributes) r)
					.map(ServletRequestAttributes::getRequest)
					.orElse(null);
			if (request == null) {
				return;
			}
			List<String> allowedHeaders = StreamEx.of(request.getHeaderNames())
					.map(this::standardizeHeader)
					.filter(allowedHeadersForward::contains)
					.toList();
			for (String header : allowedHeaders) {
				List<String> headerValues = StreamEx.of(request.getHeaders(header)).toList();
				template.header(header, headerValues);
			}

		};
	}

}
