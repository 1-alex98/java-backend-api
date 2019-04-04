package org.grouporga.java.back.end.api.config.security.oauth2;

import org.grouporga.java.back.end.api.security.OrgaUserAuthenticationConverter;
import org.grouporga.java.back.end.api.config.OrgaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class OAuthJwtConfig {

  private final OrgaProperties orgaProperties;

  public OAuthJwtConfig(OrgaProperties orgaProperties) {
    this.orgaProperties = orgaProperties;
  }

  @Bean
  @Primary
  public DefaultTokenServices tokenServices(TokenStore tokenStore) {
    DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
    defaultTokenServices.setTokenStore(tokenStore);
    defaultTokenServices.setSupportRefreshToken(true);
    return defaultTokenServices;
  }

  @Bean
  public TokenStore tokenStore(JwtAccessTokenConverter jwtAccessTokenConverter) {
    return new JwtTokenStore(jwtAccessTokenConverter);
  }

  @Bean
  protected JwtAccessTokenConverter jwtAccessTokenConverter(OrgaUserAuthenticationConverter orgaUserAuthenticationConverter) {
    JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
    jwtAccessTokenConverter.setSigningKey(orgaProperties.getJwt().getSecret());
    ((DefaultAccessTokenConverter) jwtAccessTokenConverter.getAccessTokenConverter()).setUserTokenConverter(orgaUserAuthenticationConverter);
    return jwtAccessTokenConverter;
  }
}
