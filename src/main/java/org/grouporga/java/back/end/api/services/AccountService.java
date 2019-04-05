package org.grouporga.java.back.end.api.services;

import lombok.extern.slf4j.Slf4j;
import org.grouporga.java.back.end.api.data.domain.Account;
import org.grouporga.java.back.end.api.error.ApiException;
import org.grouporga.java.back.end.api.error.Error;
import org.grouporga.java.back.end.api.error.ErrorCode;
import org.grouporga.java.back.end.api.security.OrgaUserDetails;
import org.grouporga.java.back.end.api.data.repository.AccountRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
@Slf4j
public class AccountService {
    private static final Pattern EMAIL_PATTERN = Pattern.compile(".+@.+\\..+$");
    private static final Pattern USERNAME_PATTERN = Pattern.compile("[A-Za-z][A-Za-z0-9_-]{2,15}$");
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;

    public AccountService(PasswordEncoder passwordEncoder, AccountRepository accountRepository) {
        this.passwordEncoder = passwordEncoder;
        this.accountRepository = accountRepository;
    }

    public void register(String username, String email, String password, String firstName, String surName) {
        log.info("Registration requested for user: {}", username);
        Assert.notNull(username, "The parameter 'username' must not be null");
        Assert.notNull(username, "The parameter 'password' must not be null");
        Assert.notNull(username, "The parameter 'email' must not be null");

        if(!EMAIL_PATTERN.matcher(email).matches()){
            throw new ApiException(new Error(ErrorCode.MALFORMATTED_EMAIL,email));
        }

        if(accountRepository.findByEmailIgnoreCase(email).isPresent()){
            throw new ApiException(new Error(ErrorCode.EMAIL_TAKEN,email));
        }

        if(accountRepository.existsByDisplayNameIgnoreCase(username)){
            throw new ApiException(new Error(ErrorCode.USERNAME_TAKEN,username));
        }

        if (!USERNAME_PATTERN.matcher(username).matches()) {
            throw new ApiException(new Error(ErrorCode.USERNAME_INVALID, username));
        }
        Account account = new Account();
        account.setDisplayName(username);
        account.setEmail(email);
        account.setFirstName(firstName);
        account.setSurName(surName);
        account.setPassword(passwordEncoder.encode(password));
        accountRepository.save(account);
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
        return accountRepository.findById(userId).orElseThrow(() -> new ApiException(new Error(ErrorCode.OAUTH_TOKEN_INVALID)));
    }

    public Optional<Account> findAccountByEmail(String email) {
        return accountRepository.findByEmailIgnoreCase(email);
    }

}
