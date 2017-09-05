package org.apereo.cas.support.cookiex;

import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.CipherExecutor;
import org.apereo.cas.util.cipher.NoOpCipherExecutor;
import org.apereo.cas.web.support.DefaultCasCookieValueManager;
import org.apereo.cas.web.support.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class RoamingCasCookieValueManager extends DefaultCasCookieValueManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoamingCasCookieValueManager.class);
    private static final char COOKIE_FIELD_SEPARATOR = '@';
    private static final int COOKIE_FIELDS_LENGTH = 3;

    private CipherExecutor<String, String> cipherExecutor;

    public RoamingCasCookieValueManager(final CipherExecutor cipherExecutor) {
        super(cipherExecutor);
        this.cipherExecutor = cipherExecutor;
    }

    @Override
    public String obtainCookieValue(final Cookie cookie, final HttpServletRequest request) {
        final String cookieValue = this.cipherExecutor.decode(cookie.getValue());
        LOGGER.debug("Decoded cookie value is [{}]", cookieValue);
        if (StringUtils.isBlank(cookieValue)) {
            LOGGER.debug("Retrieved decoded cookie value is blank. Failed to decode cookie [{}]", cookie.getName());
            return null;
        }

        final String[] cookieParts = cookieValue.split(String.valueOf(COOKIE_FIELD_SEPARATOR));
        if (cookieParts.length != COOKIE_FIELDS_LENGTH) {
            throw new IllegalStateException("Invalid cookie. Required fields are missing");
        }
        final String value = cookieParts[0];
        final String remoteAddr = cookieParts[1];
        final String userAgent = cookieParts[2];

        if (StringUtils.isBlank(value) || StringUtils.isBlank(remoteAddr)
                || StringUtils.isBlank(userAgent)) {
            throw new IllegalStateException("Invalid cookie. Required fields are empty");
        }

        final String agent = WebUtils.getHttpServletRequestUserAgent(request);
        if (!userAgent.equals(agent)) {
            throw new IllegalStateException("Invalid cookie. Required user-agent does not match " + agent);
        }
        return value;
    }
}
