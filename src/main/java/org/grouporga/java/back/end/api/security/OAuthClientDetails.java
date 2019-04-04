package org.grouporga.java.back.end.api.security;

import org.grouporga.java.back.end.api.data.domain.OAuthClient;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Wraps an {@link OAuthClient} and maps its fields to Spring's {@link ClientDetails}.
 */
public class OAuthClientDetails extends BaseClientDetails {

  public OAuthClientDetails(OAuthClient oAuthClient) {
    super(oAuthClient.getClientId().toString(),
      null,
      commaSeparated(oAuthClient.getDefaultScope()),
      "authorization_code,refresh_token,implicit,password,client_credentials",
      null,
      commaSeparated(oAuthClient.getRedirectUris()));
    setClientSecret(oAuthClient.getClientSecret());

    List<String> scopes = Arrays.asList((String[]) commaSeparated(oAuthClient.getDefaultScope()).split(","));
    boolean autoApproveScopes = Optional.ofNullable(oAuthClient.isAutoApproveScopes()).orElse(false);
    setAutoApproveScopes(autoApproveScopes ? scopes : Collections.emptyList());
  }

  /**
   * Flask expected multiple values to be space-separated which is why in the database, values are space-separated.
   * Spring, however, expect them to be comma-separated.
   */
  private static String commaSeparated(String defaultScope) {
    return defaultScope.replace(' ', ',');
  }
}
