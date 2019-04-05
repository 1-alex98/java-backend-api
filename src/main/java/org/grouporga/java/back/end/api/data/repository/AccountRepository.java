package org.grouporga.java.back.end.api.data.repository;

import org.grouporga.java.back.end.api.data.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

  boolean existsByDisplayNameIgnoreCase(String displayName);

  Optional<Account> findByEmailIgnoreCase(String email);
}
