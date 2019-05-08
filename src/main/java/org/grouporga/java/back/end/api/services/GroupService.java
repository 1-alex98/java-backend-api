package org.grouporga.java.back.end.api.services;

import lombok.extern.slf4j.Slf4j;
import org.grouporga.java.back.end.api.data.domain.Account;
import org.grouporga.java.back.end.api.data.domain.GroupMemberShip;
import org.grouporga.java.back.end.api.data.domain.GroupOfUsers;
import org.grouporga.java.back.end.api.data.repository.GroupMembershipRepository;
import org.grouporga.java.back.end.api.data.repository.GroupRepository;
import org.grouporga.java.back.end.api.error.ApiException;
import org.grouporga.java.back.end.api.error.Error;
import org.grouporga.java.back.end.api.error.ErrorCode;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GroupService {
    private final GroupRepository groupRepository;
    private final GroupMembershipRepository groupMembershipRepository;

    public GroupService(GroupRepository groupRepository, GroupMembershipRepository groupMembershipRepository){
        this.groupRepository = groupRepository;
        this.groupMembershipRepository = groupMembershipRepository;
    }


    public void joinViaPassword(Account account, String password, Integer groupId) {
        Optional<GroupOfUsers> groupToJoin = groupRepository.findById(groupId);
        if(!groupToJoin.isPresent()){
            throw new ApiException(new Error(ErrorCode.GROUP_DOES_NOT_EXIST,groupId));
        }
        List<GroupMemberShip> allByAccount = groupMembershipRepository.findAllByAccount(account);
        List<GroupOfUsers> groupsOfUser = allByAccount.stream()
                .map(GroupMemberShip::getGroupOfUsers)
                .collect(Collectors.toList());
        if(groupsOfUser.contains(groupToJoin.get())){
            throw new ApiException(new Error(ErrorCode.ALREADY_MEMBER_OF_GROUP,groupId));
        }
        if(!groupToJoin.get().getPassword().equals(password)){
            throw new ApiException(new Error(ErrorCode.ALREADY_MEMBER_OF_GROUP,groupId));
        }

        GroupMemberShip groupMemberShip = new GroupMemberShip();
        groupMemberShip.setAccount(account);
        groupMemberShip.setRole(GroupMemberShip.Role.USER);
        groupMemberShip.setGroupOfUsers(groupToJoin.get());
        groupMembershipRepository.save(groupMemberShip);
    }
}
