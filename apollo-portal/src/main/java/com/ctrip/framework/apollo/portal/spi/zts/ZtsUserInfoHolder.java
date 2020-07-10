package com.ctrip.framework.apollo.portal.spi.zts;

import com.ctrip.framework.apollo.portal.entity.bo.UserInfo;
import com.ctrip.framework.apollo.portal.spi.UserInfoHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;

/**
 * @author roc
 * @date 2020/6/11 17:25
 */
public class ZtsUserInfoHolder implements UserInfoHolder {

    @Override
    public UserInfo getUser() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(getCurrentUsername());
        return userInfo;
    }

    private String getCurrentUsername() {
        SecurityContextHolder.getContext().getAuthentication().getDetails();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        if (principal instanceof Principal) {
            return ((Principal) principal).getName();
        }
        return String.valueOf(principal);
    }

}
