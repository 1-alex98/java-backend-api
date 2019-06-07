package org.grouporga.java.back.end.api.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.grouporga.java.back.end.api.security.OrgaUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * Provides the route {@code /me} which returns the currently logged in user's information.
 */
@RestController
public class MeController {

    @RequestMapping(method = RequestMethod.GET, value = "/me")
    @ApiOperation(value = "Returns the authentication object of the current user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success with JSON { id: ?, type: 'player'}"),
            @ApiResponse(code = 400, message = "Bad Request")})
    public void me(HttpServletRequest request,
                   HttpServletResponse response,
                   @AuthenticationPrincipal OrgaUserDetails authentication) throws IOException {
        response.sendRedirect(String.format("/data/account/%d?%s", authentication.getId(), Objects.toString(request.getQueryString(), "")));
    }
}