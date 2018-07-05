package org.apereo.cas.support.cookiex;

import com.google.common.base.Splitter;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.CipherExecutor;
import org.apereo.cas.util.HttpRequestUtils;
import org.apereo.cas.web.support.DefaultCasCookieValueManager;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;


public class RoamingCasCookieValueManager extends DefaultCasCookieValueManager {
    private static final char COOKIE_FIELD_SEPARATOR = '@';
    private static final int COOKIE_FIELDS_LENGTH = 3;

    public RoamingCasCookieValueManager(final CipherExecutor<Serializable, Serializable> cipherExecutor) {
        super(cipherExecutor);
    }

    @Override
    protected String obtainValueFromCompoundCookie(final String cookieValue, final HttpServletRequest request) {
        final List<String> cookieParts = Splitter.on(String.valueOf(COOKIE_FIELD_SEPARATOR)).splitToList(cookieValue);
        if (cookieParts.size() != COOKIE_FIELDS_LENGTH) {
            throw new IllegalStateException("Invalid cookie. Required fields are missing");
        }
        final String value = cookieParts.get(0);
        final String remoteAddr = cookieParts.get(1);
        final String userAgent = cookieParts.get(2);

        if (StringUtils.isBlank(value) || StringUtils.isBlank(remoteAddr) || StringUtils.isBlank(userAgent)) {
            throw new IllegalStateException("Invalid cookie. Required fields are empty");
        }

        final String agent = HttpRequestUtils.getHttpServletRequestUserAgent(request);
        if (!userAgent.equals(agent)) {
            throw new IllegalStateException("Invalid cookie. Required user-agent " + userAgent + " does not match " + agent);
        }
        return value;
    }

}