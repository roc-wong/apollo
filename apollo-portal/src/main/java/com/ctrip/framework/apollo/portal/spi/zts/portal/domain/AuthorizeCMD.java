package com.ctrip.framework.apollo.portal.spi.zts.portal.domain;

/**
 * 4.1.11新增用户的系统准入权限
 * 前提条件:
 * • 存在该应用系统对应的系统准入权限
 * • 存在某一逻辑组，已分配了该系统的准入权限
 * @author roc
 * @since 2020/6/12 13:24
 */
public class AuthorizeCMD {

    /**
     * 登录用户名/身份证/邮箱/手机号/员工编号
     */
    private String loginKey;
    /**
     * 应用系统的代码
     */
    private String appCode;

    public String getLoginKey() {
        return loginKey;
    }

    public void setLoginKey(String loginKey) {
        this.loginKey = loginKey;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    @Override
    public String toString() {
        return "{\"loginKey\":" + (loginKey == null ? "null" : "\"" + loginKey + "\"") + ", " +
                "\"appCode\":" + (appCode == null ? "null" : "\"" + appCode + "\"") +
                "}";
    }
}
