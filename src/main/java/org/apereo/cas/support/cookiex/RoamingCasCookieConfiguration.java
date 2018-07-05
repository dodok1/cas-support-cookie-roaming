package org.apereo.cas.support.cookiex;

import org.apereo.cas.CipherExecutor;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.web.support.CookieValueManager;
import org.apereo.cas.web.support.NoOpCookieValueManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

@Configuration("roamingCasCookieConfiguration")
@EnableConfigurationProperties(CasConfigurationProperties.class)
public class RoamingCasCookieConfiguration {
    @Autowired
    private CasConfigurationProperties casProperties;

    @Bean(name = {"roamingCookieValueManager", "cookieValueManager"})
    public CookieValueManager cookieValueManager(@Qualifier("cookieCipherExecutor") final CipherExecutor<Serializable, Serializable> cipherExecutor) {
        if (casProperties.getTgc().getCrypto().isEnabled()) {
            return new RoamingCasCookieValueManager(cipherExecutor);
        }
        return new NoOpCookieValueManager();
    }
}
