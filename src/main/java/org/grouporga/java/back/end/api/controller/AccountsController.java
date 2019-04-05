package org.grouporga.java.back.end.api.controller;

import io.swagger.annotations.ApiOperation;
import org.grouporga.java.back.end.api.error.ApiException;
import org.grouporga.java.back.end.api.error.Error;
import org.grouporga.java.back.end.api.error.ErrorCode;
import org.grouporga.java.back.end.api.security.OAuthScope;
import org.grouporga.java.back.end.api.services.AccountService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "/accounts")
public class AccountsController {
    private final AccountService accountService;

    public AccountsController(AccountService accountService) {
        this.accountService = accountService;
    }

    @ApiOperation("Registers a new account.")
    @PreAuthorize("#oauth2.hasScope('" + OAuthScope._CREATE_USER + "')")
    @RequestMapping(method = RequestMethod.POST, value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public void register(HttpServletRequest request,
                           @RequestParam("username") String username,
                           @RequestParam("email") String email,
                           @RequestParam("password") String password,
                           @RequestParam(value = "firstName", required = false) String firstName,
                           @RequestParam(value = "surName", required = false) String surName) {
        if (request.isUserInRole("USER")) {
            throw new ApiException(new Error(ErrorCode.ALREADY_REGISTERED));
        }
        accountService.register(username,email,password,firstName,surName);
    }
}
