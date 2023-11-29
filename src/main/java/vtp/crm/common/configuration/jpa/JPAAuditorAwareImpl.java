package vtp.crm.common.configuration.jpa;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.AuditorAware;
import vtp.crm.common.utils.ContextUtils;
import vtp.crm.common.vo.dto.token.CurrentUserInfo;

import java.util.Optional;

public class JPAAuditorAwareImpl implements AuditorAware<Long> {

	private static final Logger logger = LogManager.getLogger(JPAAuditorAwareImpl.class);

	@Override
	public Optional<Long> getCurrentAuditor() {
		try {
			CurrentUserInfo currentUserInfo = ContextUtils.getCurrentUserInfo();
			return Optional.ofNullable(currentUserInfo).map(CurrentUserInfo::getUserId);
		} catch (Exception ex) {
			logger.error("Not find detail user: {}", ex);
		}

		return Optional.empty();
	}

}
