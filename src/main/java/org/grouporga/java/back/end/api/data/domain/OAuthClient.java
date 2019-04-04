package org.grouporga.java.back.end.api.data.domain;

import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.UUID;


@Entity
@Table(name = "oauth_client")
@Setter
public class OAuthClient extends AbstractIntegerIdEntity {

  private String name;
  private UUID clientId;
  private String clientSecret;
  private String redirectUris;
  private String defaultRedirectUri;
  private String defaultScope;
  private String iconUrl;
  private Boolean autoApproveScopes;

  @Column(name = "name")
  public @NotNull String getName() {
    return name;
  }

  @Column(name = "client_id")
  public @NotNull UUID getClientId() {
    return clientId;
  }

  @Column(name = "client_secret")
  public @NotNull String getClientSecret() {
    return clientSecret;
  }

  @Column(name = "redirect_uris")
  public @NotNull String getRedirectUris() {
    return redirectUris;
  }

  @Column(name = "default_redirect_uri")
  public @NotNull String getDefaultRedirectUri() {
    return defaultRedirectUri;
  }

  @Column(name = "default_scope")
  public @NotNull String getDefaultScope() {
    return defaultScope;
  }

  @Column(name = "icon_url")
  public String getIconUrl() {
    return iconUrl;
  }

  @Column(name = "auto_approve_scopes")
  public Boolean isAutoApproveScopes() {
    return autoApproveScopes;
  }

}
