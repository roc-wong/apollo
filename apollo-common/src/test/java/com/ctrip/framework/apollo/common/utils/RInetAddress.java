package com.ctrip.framework.apollo.common.utils;

/**
 * @author roc
 * @date 2019/2/25 15:36
 */
public class RInetAddress {

    private String hostAddress;

    public RInetAddress(String hostAddress) {
        this.hostAddress = hostAddress;
    }

    public String getHostAddress() {
        return hostAddress;
    }

    public void setHostAddress(String hostAddress) {
        this.hostAddress = hostAddress;
    }
}
