package org.grouporga.java.back.end.api.security;

import com.google.common.base.Strings;
import lombok.Getter;
import org.grouporga.java.back.end.api.data.domain.Account;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class OrgaUserDetails extends org.springframework.security.core.userdetails.User {

  private final Integer id;

  public OrgaUserDetails(Account account, Collection<? extends GrantedAuthority> authorities) {
    this(
      account.getId(),
      account.getDisplayName(),
      account.getPassword(),
      !account.isLocked(),
      authorities
    );
  }

  public OrgaUserDetails(Integer id, String username, String password, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
    super(username, Strings.nullToEmpty(password), true, true, true, accountNonLocked, authorities);
    this.id = id;
  }
}
