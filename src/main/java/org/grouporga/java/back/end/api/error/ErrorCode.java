package org.grouporga.java.back.end.api.error;

import lombok.Getter;

@Getter
public enum ErrorCode {
  ALREADY_REGISTERED(101, "Registration failed", "You can't create a new account because you already have one."),
  MALFORMATTED_EMAIL(102, "Bad email format", "You are sure that ''{0}'' is a valid email???"),
  EMAIL_TAKEN(103, "Email taken", "There already exists an account with the email ''{0}''."),
  USERNAME_TAKEN(104, "Display name/username taken", "There already exists an account with that name ''{0}''."),
  USERNAME_INVALID(105, "Display name/username invalid", "You used forbidden characters in the username."),
  VALIDATION_FAILED(106, "Validation failed", "{0}"),
  OAUTH_TOKEN_INVALID(107, "OAUTH token invalid", "That should not happen is a token you got from test???");

  private final int code;
  private final String title;
  private final String detail;

  ErrorCode(int code, String title, String detail) {
    this.code = code;
    this.title = title;
    this.detail = detail;
  }

  public String codeAsString() {
    return String.valueOf(code);
  }
}
