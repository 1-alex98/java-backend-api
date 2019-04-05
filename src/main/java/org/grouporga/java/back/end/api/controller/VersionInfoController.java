package org.grouporga.java.back.end.api.controller;

import org.grouporga.java.back.end.api.config.OrgaProperties;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
public class VersionInfoController {
    private final OrgaProperties orgaProperties;

    @Inject
    public VersionInfoController(OrgaProperties orgaProperties) {
        this.orgaProperties = orgaProperties;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/version", produces = MediaType.TEXT_PLAIN_VALUE)
    public String me() {
        return orgaProperties.getVersion();
    }

}
