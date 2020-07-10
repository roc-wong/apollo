package com.ctrip.framework.apollo.portal.spi.zts.portal.domain;

/**
 * 4.1.11新增用户的系统准入权限
 * 前提条件:
 * • 存在该应用系统对应的系统准入权限
 * • 存在某一逻辑组，已分配了该系统的准入权限
 * @author roc
 * @since 2020/6/12 13:24
 */
public class AuthorizeDTO {


    /**
     * appCode : M0066
     * appName : APOLLO配置中心
     * description : 网金配置中心-何君
     * uniauthType : 0
     * redirectUrl :
     * appSecret :
     * status : 1
     * authInfo : {"proxyPolicy":{"@class":"org.jasig.cas.services.RegexMatchingRegisteredServiceProxyPolicy","pattern":"http://.*"},"logoutType":"BACK_CHANNEL","@class":"org.jasig.cas.services.RegexRegisteredService","evaluationOrder":1,"name":"M0066","usernameAttributeProvider":{"@class":"com.zts.cas.custom.services.CustomRegisteredServiceUsernameProvider"},"description":"APOLLO\u914d\u7f6e\u4e2d\u5fc3","theme":"M0066","id":10000066,"serviceId":"^http://10.25.102.174.*","attributeReleasePolicy":{"authorizedToReleaseCredentialPassword":false,"@class":"org.jasig.cas.services.ReturnAllowedAttributeReleasePolicy","principalAttributesRepository":{"@class":"org.jasig.cas.authentication.principal.DefaultPrincipalAttributesRepository"},"authorizedToReleaseProxyGrantingTicket":false},"accessStrategy":{"@class":"org.jasig.cas.services.DefaultRegisteredServiceAccessStrategy","ssoEnabled":true,"enabled":true}}
     * unifiedFlag : 1
     * ips :
     * notifyUrl :
     * oauthEnable : 0
     * id : 105
     */

    private String appCode;
    private String appName;
    private String description;
    private String uniAuthType;
    private String redirectUrl;
    private String appSecret;
    private String status;
    private String authInfo;
    private String unifiedFlag;
    private String ips;
    private String notifyUrl;
    private String oauthEnable;
    private int id;

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUniAuthType() {
        return uniAuthType;
    }

    public void setUniAuthType(String uniAuthType) {
        this.uniAuthType = uniAuthType;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAuthInfo() {
        return authInfo;
    }

    public void setAuthInfo(String authInfo) {
        this.authInfo = authInfo;
    }

    public String getUnifiedFlag() {
        return unifiedFlag;
    }

    public void setUnifiedFlag(String unifiedFlag) {
        this.unifiedFlag = unifiedFlag;
    }

    public String getIps() {
        return ips;
    }

    public void setIps(String ips) {
        this.ips = ips;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getOauthEnable() {
        return oauthEnable;
    }

    public void setOauthEnable(String oauthEnable) {
        this.oauthEnable = oauthEnable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "{\"_class\":\"AuthorizeDTO\", " +
                "\"appCode\":" + (appCode == null ? "null" : "\"" + appCode + "\"") + ", " +
                "\"appName\":" + (appName == null ? "null" : "\"" + appName + "\"") + ", " +
                "\"description\":" + (description == null ? "null" : "\"" + description + "\"") + ", " +
                "\"uniAuthType\":" + (uniAuthType == null ? "null" : "\"" + uniAuthType + "\"") + ", " +
                "\"redirectUrl\":" + (redirectUrl == null ? "null" : "\"" + redirectUrl + "\"") + ", " +
                "\"appSecret\":" + (appSecret == null ? "null" : "\"" + appSecret + "\"") + ", " +
                "\"status\":" + (status == null ? "null" : "\"" + status + "\"") + ", " +
                "\"authInfo\":" + (authInfo == null ? "null" : "\"" + authInfo + "\"") + ", " +
                "\"unifiedFlag\":" + (unifiedFlag == null ? "null" : "\"" + unifiedFlag + "\"") + ", " +
                "\"ips\":" + (ips == null ? "null" : "\"" + ips + "\"") + ", " +
                "\"notifyUrl\":" + (notifyUrl == null ? "null" : "\"" + notifyUrl + "\"") + ", " +
                "\"oauthEnable\":" + (oauthEnable == null ? "null" : "\"" + oauthEnable + "\"") + ", " +
                "\"id\":\"" + id + "\"" +
                "}";
    }
}
