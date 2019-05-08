package org.grouporga.java.back.end.api.controller;

import io.swagger.annotations.ApiOperation;
import org.grouporga.java.back.end.api.services.AccountService;
import org.grouporga.java.back.end.api.services.GroupService;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "/groupMemberships")
public class GroupMembershipController {
    private final GroupService groupService;
    private final AccountService accountService;

    public GroupMembershipController(GroupService groupService, AccountService accountService) {
        this.groupService = groupService;
        this.accountService = accountService;
    }

    @ApiOperation("Join a group.")
    @RequestMapping(method = RequestMethod.POST, value = "/joinViaPassword", produces = MediaType.APPLICATION_JSON_VALUE)
    public void register(HttpServletRequest request,
                            @RequestParam("password") String password,
                            @RequestParam("group_id") Integer groupId,
                            Authentication authentication) {
        if(authentication==null) throw new SecurityException("must be logged in for this action");
        groupService.joinViaPassword(accountService.getAccount(authentication),password,groupId);
    }
}
