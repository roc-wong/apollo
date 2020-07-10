package com.ctrip.framework.apollo.portal.spi.zts;

import com.ctrip.framework.apollo.portal.entity.po.UserPO;
import org.jasig.cas.client.validation.AssertionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.cas.authentication.CasAssertionAuthenticationToken;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

/**
 *
 * @author roc
 * @since 2020/6/10 13:20
 */
public class ZtsUserDetailsService implements AuthenticationUserDetailsService<CasAssertionAuthenticationToken> {

    public static final String EMAIL_POSTFIX = "@zts.com.cn";

    @Autowired
    private RabcUserService rabcUserService;
    @Autowired
    private JdbcUserDetailsManager userDetailsManager;

    @Override
    public UserDetails loadUserDetails(CasAssertionAuthenticationToken token) throws UsernameNotFoundException {
        AssertionImpl assertion = (AssertionImpl) token.getAssertion();
        String loginUserName = assertion.getPrincipal().getName();
        String portalEmail = loginUserName + EMAIL_POSTFIX;

        UserPO userPO = rabcUserService.findByUsernameOrEmail(loginUserName, portalEmail);
        String userName;
        //首次使用CAS登录，同步用户信息到ApolloPortalDB.Users表，密码使用随机生成的密码
        if (userPO == null) {
            rabcUserService.save(loginUserName, portalEmail);
            userName = loginUserName;
        } else {
            userName = loginUserName.equalsIgnoreCase(userPO.getUsername()) ? loginUserName : userPO.getUsername();
        }
        return userDetailsManager.loadUserByUsername(userName);
    }
}
