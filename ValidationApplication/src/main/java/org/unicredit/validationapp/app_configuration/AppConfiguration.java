package org.unicredit.validationapp.app_configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.unicredit.validationapp.domain.view_models.IbanInformation;

@Configuration
public class AppConfiguration {
    @Bean
    public IbanInformation iban() {
        return new IbanInformation();
    }
}
