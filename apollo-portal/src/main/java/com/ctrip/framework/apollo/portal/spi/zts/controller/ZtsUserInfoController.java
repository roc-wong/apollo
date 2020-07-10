package com.ctrip.framework.apollo.portal.spi.zts.controller;

import com.ctrip.framework.apollo.common.exception.BadRequestException;
import com.ctrip.framework.apollo.core.utils.StringUtils;
import com.ctrip.framework.apollo.portal.component.config.PortalConfig;
import com.ctrip.framework.apollo.portal.entity.po.UserPO;
import com.ctrip.framework.apollo.portal.spi.zts.portal.domain.ApiResponse;
import com.ctrip.framework.apollo.portal.spi.zts.portal.domain.AuthorizeCMD;
import com.ctrip.framework.apollo.portal.spi.zts.portal.domain.AuthorizeDTO;
import com.ctrip.framework.apollo.portal.spi.zts.util.HttpStatusClass;
import com.google.common.base.Splitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.List;

@Profile("zts")
@RestController
public class ZtsUserInfoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZtsUserInfoController.class);

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private PortalConfig portalConfig;

    private String authorizeUrl;

    @PostConstruct
    public void init(){
        this.authorizeUrl = portalConfig.casServerName() + "v1/users/applications";
    }

    @PreAuthorize(value = "@permissionValidator.isSuperAdmin()")
    @PostMapping("/oaUsers/authorize")
    public boolean authorize(@RequestBody UserPO user) {
        if (StringUtils.isContainEmpty(user.getUsername())) {
            throw new BadRequestException("Username can not be empty.");
        }
        List<String> userNames = Splitter.on(",").omitEmptyStrings().splitToList(user.getUsername());

        LOGGER.error("email = {}", user.getUsername());

        for (String userName : userNames) {
            authorizeUser(userName);
        }
        return true;
    }

    private void authorizeUser(String userName) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);

        AuthorizeCMD authorizeCMD = new AuthorizeCMD();
        authorizeCMD.setLoginKey(userName);
        authorizeCMD.setAppCode(portalConfig.casClientAppCode());
        HttpEntity<String> entity = new HttpEntity<>(authorizeCMD.toString(), httpHeaders);
        ResponseEntity<ApiResponse<AuthorizeDTO>> responseEntity = restTemplate.exchange(authorizeUrl, HttpMethod.POST, entity, new ParameterizedTypeReference<ApiResponse<AuthorizeDTO>>() {
        });
        if (!HttpStatusClass.SUCCESS.contains(responseEntity.getStatusCodeValue())){
            ApiResponse<AuthorizeDTO> apiResponse = responseEntity.getBody();
            throw new BadRequestException(apiResponse.getException() + apiResponse.getMessage());
        }
    }


    @PreAuthorize(value = "@permissionValidator.isSuperAdmin()")
    @PostMapping("/oaUsers/cancelAuthorization")
    public boolean cancelAuthorization(@RequestBody UserPO user) {
        if (StringUtils.isContainEmpty(user.getUsername())) {
            throw new BadRequestException("Username can not be empty.");
        }
        List<String> userNames = Splitter.on(",").omitEmptyStrings().splitToList(user.getUsername());

        LOGGER.error("userNames = {}", user.getUsername());

        for (String userName : userNames) {
            cancelAuthorizeUser(userName);
        }

        return true;
    }

    private void cancelAuthorizeUser(String userName) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);

        AuthorizeCMD authorizeCMD = new AuthorizeCMD();
        authorizeCMD.setLoginKey(userName);
        authorizeCMD.setAppCode(portalConfig.casClientAppCode());
        HttpEntity<String> entity = new HttpEntity<>(authorizeCMD.toString(), httpHeaders);
        String url = authorizeUrl + "?loginKey={loginKey}&appCode={appCode}";
        ResponseEntity<ApiResponse<AuthorizeDTO>> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, entity, new ParameterizedTypeReference<ApiResponse<AuthorizeDTO>>() {
        }, userName, portalConfig.casClientAppCode());
        if (!HttpStatusClass.SUCCESS.contains(responseEntity.getStatusCodeValue())){
            throw new BadRequestException(responseEntity.getBody().toString());
        }
    }


}
