package org.grouporga.java.back.end.api.security.user;


import lombok.extern.slf4j.Slf4j;
import org.grouporga.java.back.end.api.error.ApiException;
import org.grouporga.java.back.end.api.security.OrgaUserDetails;
import org.grouporga.java.back.end.api.data.domain.Account;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class AccountService {
  private final AccountRepository accountRepository;

  public AccountService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  public Account getAccount(Authentication authentication) {
    if (authentication != null
      && authentication.getPrincipal() != null
      && authentication.getPrincipal() instanceof OrgaUserDetails) {
      return getAccount(((OrgaUserDetails) authentication.getPrincipal()).getId());
    }
    throw new SecurityException("token invalid");
  }

  public Account getAccount(Integer userId) {
    return accountRepository.findById(userId).orElseThrow(() -> new ApiException(new Error("token invalid")));
  }

  public Optional<Account> findAccountByEmail(String email) {
    return accountRepository.findByEmailIgnoreCase(email);
  }

}
