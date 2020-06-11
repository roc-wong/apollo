package com.ctrip.framework.apollo.portal.spi.zts;

import com.ctrip.framework.apollo.core.utils.StringUtils;
import com.ctrip.framework.apollo.portal.entity.bo.UserInfo;
import com.ctrip.framework.apollo.portal.entity.po.Authorities;
import com.ctrip.framework.apollo.portal.entity.po.UserPO;
import com.ctrip.framework.apollo.portal.repository.AuthoritiesRepository;
import com.ctrip.framework.apollo.portal.repository.UserRepository;
import com.ctrip.framework.apollo.portal.spi.UserService;
import com.google.common.collect.Lists;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 基于RBAC实现的用户服务，基本上保留了Apollo基于SpringSecurity的用户体系
 * @author roc
 * @since 2020/6/11 14:14
 */
public class RabcUserService implements UserService {

    private static final String SIMPLE_GRANTED_AUTHORITY = "ROLE_user";

    private PasswordEncoder encoder = new BCryptPasswordEncoder();
    private List<GrantedAuthority> authorities;

    @Autowired
    private JdbcUserDetailsManager userDetailsManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthoritiesRepository authoritiesRepository;

    @PostConstruct
    public void init() {
        authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_user"));
    }

    @Transactional
    public UserPO save(String username, String portalEmail) {
        String randomPwd = RandomStringUtils.randomAlphanumeric(10);
        UserPO userPO = new UserPO();
        userPO.setUsername(username);
        userPO.setEmail(portalEmail);
        userPO.setPassword(encoder.encode(randomPwd));
        userPO.setEnabled(1);
        UserPO userDO = userRepository.save(userPO);

        Authorities authorities = new Authorities();
        authorities.setUsername(username);
        authorities.setAuthority(SIMPLE_GRANTED_AUTHORITY);
        authoritiesRepository.save(authorities);

        return userDO;
    }

    @Override
    public List<UserInfo> searchUsers(String keyword, int offset, int limit) {
        List<UserPO> users;
        if (StringUtils.isEmpty(keyword)) {
            users = userRepository.findFirst20ByEnabled(1);
        } else {
            users = userRepository.findByUsernameLikeAndEnabled("%" + keyword + "%", 1);
        }

        List<UserInfo> result = Lists.newArrayList();
        if (CollectionUtils.isEmpty(users)) {
            return result;
        }

        result.addAll(users.stream().map(UserPO::toUserInfo).collect(Collectors.toList()));

        return result;
    }

    @Override
    public UserInfo findByUserId(String userId) {
        UserPO userPO = userRepository.findByUsername(userId);
        return userPO == null ? null : userPO.toUserInfo();
    }

    @Override
    public List<UserInfo> findByUserIds(List<String> userIds) {
        List<UserPO> users = userRepository.findByUsernameIn(userIds);

        if (CollectionUtils.isEmpty(users)) {
            return Collections.emptyList();
        }

        List<UserInfo> result = Lists.newArrayList();

        result.addAll(users.stream().map(UserPO::toUserInfo).collect(Collectors.toList()));

        return result;
    }

    public UserPO findByUsernameOrEmail(String loginUserName, String portalEmail) {
        return userRepository.findByUsernameOrEmail(loginUserName,portalEmail);
    }

    public List<Authorities> findUserAuthorities(String username){
        return authoritiesRepository.findByUsername(username);
    }
}
