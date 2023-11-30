package vtp.crm.common.configuration.jpa;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages={"vtp.crm.*"})
@EnableJpaRepositories(basePackages = { "vtp.crm.*" })
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JPAConfiguration {

    @Bean
    AuditorAware<Long> auditorProvider() {
        return new JPAAuditorAwareImpl();
    }

}
