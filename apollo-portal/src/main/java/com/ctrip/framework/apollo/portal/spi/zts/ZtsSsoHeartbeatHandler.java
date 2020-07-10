package com.ctrip.framework.apollo.portal.spi.zts;

import com.ctrip.framework.apollo.portal.spi.SsoHeartbeatHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author roc
 * @date 2020/6/11 17:24
 */
public class ZtsSsoHeartbeatHandler implements SsoHeartbeatHandler {

    @Override
    public void doHeartbeat(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.sendRedirect("default_sso_heartbeat.html");
        } catch (IOException e) {
        }
    }

}
