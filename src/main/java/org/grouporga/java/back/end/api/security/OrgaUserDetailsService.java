package org.grouporga.java.back.end.api.security;

import lombok.extern.slf4j.Slf4j;
import org.grouporga.java.back.end.api.data.domain.Account;
import org.grouporga.java.back.end.api.security.user.AccountService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Adapter between Spring's {@link UserDetailsService} and FAF's {@code login} table.
 */
@Service
@Slf4j
public class OrgaUserDetailsService implements UserDetailsService {

  private final AccountService accountService;

  public OrgaUserDetailsService(AccountService accountService) {
    this.accountService = accountService;
  }

  @Override
  @Transactional
  public OrgaUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Account account = accountService.findAccountByEmail(email)
      .orElseThrow(() -> new UsernameNotFoundException("User could not be found: " + email));

    ArrayList<GrantedAuthority> authorities = new ArrayList<>();

    return new OrgaUserDetails(account, authorities);
  }

}
