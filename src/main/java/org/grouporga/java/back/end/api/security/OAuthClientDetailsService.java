package org.grouporga.java.back.end.api.security;

import org.grouporga.java.back.end.api.config.OrgaProperties;
import org.grouporga.java.back.end.api.data.domain.OAuthClient;
import org.grouporga.java.back.end.api.data.repository.OAuthClientRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OAuthClientDetailsService implements ClientDetailsService {

  public static final String CLIENTS_CACHE_NAME = "OAuthClientDetailsService.oAuthClients";
  private final OAuthClientRepository oAuthClientRepository;
  private final OrgaProperties orgaProperties;

  public OAuthClientDetailsService(OAuthClientRepository oAuthClientRepository, OrgaProperties orgaProperties) {
    this.oAuthClientRepository = oAuthClientRepository;
    this.orgaProperties = orgaProperties;
  }

  @Override
  @Cacheable(CLIENTS_CACHE_NAME)
  public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
    OAuthClient oAuthClient = oAuthClientRepository.findByClientId(UUID.fromString(clientId))
        .orElseThrow(() -> new ClientRegistrationException("Unknown client: " + clientId));

    OAuthClientDetails clientDetails = new OAuthClientDetails(oAuthClient);

    OrgaProperties.Jwt jwt = orgaProperties.getJwt();
    clientDetails.setAccessTokenValiditySeconds(jwt.getAccessTokenValiditySeconds());
    clientDetails.setRefreshTokenValiditySeconds(jwt.getRefreshTokenValiditySeconds());

    return clientDetails;
  }
}
