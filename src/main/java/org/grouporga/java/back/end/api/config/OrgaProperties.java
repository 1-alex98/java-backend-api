package org.grouporga.java.back.end.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "orga", ignoreUnknownFields = false)
public class OrgaProperties {
    private final OAuth2 oAuth2 = new OAuth2();
    private final Jwt jwt = new Jwt();

    @Data
    public static class OAuth2 {
        private String resourceId = "orga-api";
    }

    @Data
    public static class Jwt {
        /**
         * The secret used for JWT token generation.
         */
        private String secret;
        private int accessTokenValiditySeconds = 3600;
        private int refreshTokenValiditySeconds = 3600;
    }
}
