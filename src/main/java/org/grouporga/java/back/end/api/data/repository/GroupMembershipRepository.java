package org.grouporga.java.back.end.api.data.repository;

import org.grouporga.java.back.end.api.data.domain.Account;
import org.grouporga.java.back.end.api.data.domain.GroupMemberShip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface GroupMembershipRepository extends JpaRepository<GroupMemberShip, Integer> {
    List<GroupMemberShip> findAllByAccount(Account account);
}
