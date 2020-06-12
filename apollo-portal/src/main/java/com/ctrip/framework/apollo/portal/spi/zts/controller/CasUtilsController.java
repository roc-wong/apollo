package com.ctrip.framework.apollo.portal.spi.zts.controller;

import com.ctrip.framework.apollo.portal.component.config.PortalConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ctrip.framework.apollo.portal.spi.zts.configuration.WebSecurityConfiguration.CAS_FILTER_PROCESSES_URL;

@Profile("zts")
@RestController
public class CasUtilsController {

    @Autowired
    private PortalConfig portalConfig;

    @RequestMapping(value = "/utils/getCasLoginUrl", produces = "application/json;charset=UTF-8")
    public String getCasServerUrl() {
        return "{\"url\":\"" + portalConfig.casServerName() + portalConfig.casServerUrlPrefix() + portalConfig.casServerLoginUrl()
                + "?service=" + portalConfig.portalClientName() + CAS_FILTER_PROCESSES_URL + "\"}";
    }
}
