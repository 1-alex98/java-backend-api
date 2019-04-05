package org.grouporga.java.back.end.api.security;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum OAuthScope {

  PUBLIC_PROFILE(OAuthScope._PUBLIC_PROFILE, "Read your public account data"),
  CREATE_USER(OAuthScope._CREATE_USER, "Create a user");

  public static final String _PUBLIC_PROFILE = "public_profile";
  public static final String _CREATE_USER = "create_user";

  private static final Map<String, OAuthScope> fromString;

  static {
    fromString = new HashMap<>();
    for (OAuthScope oAuthScope : values()) {
      fromString.put(oAuthScope.key, oAuthScope);
    }
  }

  private final String key;
  private final String title;

  OAuthScope(String key, String title) {
    this.key = key;
    this.title = title;
  }

  public static Optional<OAuthScope> fromKey(String key) {
    return Optional.ofNullable(fromString.get(key));
  }

  public String getKey() {
    return key;
  }

  public String getTitle() {
    return title;
  }
}
