package com.ctrip.framework.apollo.portal.spi.zts;

import com.ctrip.framework.apollo.portal.spi.LogoutHandler;
import com.ctrip.framework.apollo.portal.spi.SsoHeartbeatHandler;
import org.springframework.context.annotation.Bean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ZtsLogoutHandler本应在用户注销(ZtsLogoutHandler#logout)时被调用，但在zts的实现中，被LogoutFilter拦截后直接注销并转跳到蜂巢的注销页面
 * @author roc
 * @date 2020/6/11 17:19
 */
public class ZtsLogoutHandler implements LogoutHandler {

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        //do nothing
    }

    @Bean
    public SsoHeartbeatHandler ztsSsoHeartbeatHandler() {
        return new ZtsSsoHeartbeatHandler();
    }
}
