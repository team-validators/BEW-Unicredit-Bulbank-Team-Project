package org.unicredit.validationapp.app_configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.unicredit.validationapp.domain.Iban;

@Configuration
public class AppConfiguration {
    @Bean
    public Iban iban() {
        return new Iban();
    }
}
