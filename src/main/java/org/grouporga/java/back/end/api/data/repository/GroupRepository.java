package org.grouporga.java.back.end.api.data.repository;

import org.grouporga.java.back.end.api.data.domain.GroupOfUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface GroupRepository extends JpaRepository<GroupOfUsers, Integer> {

}
