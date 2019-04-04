package org.grouporga.java.back.end.api.data.repository;

import org.grouporga.java.back.end.api.data.domain.OAuthClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OAuthClientRepository extends JpaRepository<OAuthClient, Integer> {
  Optional<OAuthClient> findByClientId(UUID clientId);
}
