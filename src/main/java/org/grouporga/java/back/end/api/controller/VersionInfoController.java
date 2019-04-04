package org.grouporga.java.back.end.api.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersionInfoController {

    @RequestMapping(method = RequestMethod.GET, value = "/version", produces = MediaType.TEXT_PLAIN_VALUE)
    public String me() {
        String implementationVersion = getClass().getPackage().getImplementationVersion();
        return implementationVersion==null?"snapshot":implementationVersion;
    }
}
