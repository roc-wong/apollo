package com.ctrip.framework.apollo.common.utils;

import java.net.NetPermission;
import java.util.Enumeration;
import java.util.NoSuchElementException;

/**
 * @author roc
 * @date 2019/2/25 15:34
 */
public class RNetworkInterface {

    private RInetAddress addrs[];

    public Enumeration<RInetAddress> getInetAddresses() {

        class checkedAddresses implements Enumeration<RInetAddress> {

            private int i=0, count=0;
            private RInetAddress local_addrs[];

            checkedAddresses() {
                local_addrs = new RInetAddress[addrs.length];
                boolean trusted = true;

                SecurityManager sec = System.getSecurityManager();
                if (sec != null) {
                    try {
                        sec.checkPermission(new NetPermission("getNetworkInformation"));
                    } catch (SecurityException e) {
                        trusted = false;
                    }
                }
                for (int j=0; j<addrs.length; j++) {
                    try {
                        if (sec != null && !trusted) {
                            sec.checkConnect(addrs[j].getHostAddress(), -1);
                        }
                        local_addrs[count++] = addrs[j];
                    } catch (SecurityException e) { }
                }

            }

            public RInetAddress nextElement() {
                if (i < count) {
                    return local_addrs[i++];
                } else {
                    throw new NoSuchElementException();
                }
            }

            public boolean hasMoreElements() {
                return (i < count);
            }
        }
        return new checkedAddresses();
    }


    public RInetAddress[] getAddrs() {
        return addrs;
    }

    public void setAddrs(RInetAddress[] addrs) {
        this.addrs = addrs;
    }
}
