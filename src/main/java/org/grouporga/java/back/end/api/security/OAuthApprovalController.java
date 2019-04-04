package org.grouporga.java.back.end.api.security;

import org.grouporga.java.back.end.api.data.domain.OAuthClient;
import org.grouporga.java.back.end.api.data.repository.OAuthClientRepository;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/oauth")
@SessionAttributes("authorizationRequest")
public class OAuthApprovalController {
  private final OAuthClientRepository oAuthClientRepository;

  public OAuthApprovalController(OAuthClientRepository oAuthClientRepository) {
    this.oAuthClientRepository = oAuthClientRepository;
  }

  @GetMapping("/confirm_access")
  public ModelAndView confirmAccess(Map<String, Object> model) {
    final AuthorizationRequest authorizationRequest = (AuthorizationRequest) model.get("authorizationRequest");
    final OAuthClient client = oAuthClientRepository.findByClientId(UUID.fromString(authorizationRequest.getClientId()))
      .orElseThrow(() -> new IllegalArgumentException("No client with ID: " + authorizationRequest.getClientId()));
    Set<OAuthScope> scopes = getScopesFromScopeString(authorizationRequest.getScope());

    return new ModelAndView("oauth_confirm_access")
      .addObject("client", client)
      .addObject("scopes", scopes);
  }

  private Set<OAuthScope> getScopesFromScopeString(Set<String> scopesString) {
    return scopesString.stream()
      .filter(s -> !s.isEmpty())
      .map(OAuthScope::fromKey)
      .filter(Optional::isPresent)
      .map(Optional::get)
      .collect(Collectors.toSet());
  }
}
