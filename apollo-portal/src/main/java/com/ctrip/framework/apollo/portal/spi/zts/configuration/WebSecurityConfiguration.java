package com.ctrip.framework.apollo.portal.spi.zts.configuration;

import com.ctrip.framework.apollo.portal.component.config.PortalConfig;
import com.ctrip.framework.apollo.portal.spi.zts.RabcUserService;
import com.ctrip.framework.apollo.portal.spi.zts.ZtsLogoutHandler;
import com.ctrip.framework.apollo.portal.spi.zts.ZtsSsoHeartbeatHandler;
import com.ctrip.framework.apollo.portal.spi.zts.ZtsUserDetailsService;
import com.ctrip.framework.apollo.portal.spi.zts.ZtsUserInfoHolder;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.validation.Cas20ServiceTicketValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.sql.DataSource;

/**
 * 接入蜂巢CAS服务流程：
 * 1. 编译时启用zts profile
 * 2. 在PortalDB.serverconfig表中增加配置项：
 * casServerName = http://10.29.181.202:30080  CAS Server地址
 * casServerUrlPrefix = /cas  CAS Server contextPath, 即Url前缀
 * casServerLoginUrl = /login
 * casServerLogoutUrl = /logout
 * portalClientName = http://10.25.102.174:8070 Apollo Portal控制台地址
 * 3. 使用蜂巢OA账户登录成功后，会回调到Apollo Portal管理控制台
 *
 * @author roc
 * @date 2020/6/11 18:12
 */
@Profile("zts")
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    public static final String USER_ROLE = "user";
    public static final String CAS_FILTER_PROCESSES_URL = "/login/cas";
    public static final String PORTAL_LOGOUT_URL = "/user/logout";

    @Autowired
    private PortalConfig portalConfig;

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(casAuthenticationProvider());
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(jdbcUserDetailsManager(auth, dataSource));
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        auth.authenticationProvider(daoAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().sameOrigin();
        http.authorizeRequests()
                .antMatchers("/utils/**", "/signin", "/login/**", "/prometheus/**", "/metrics/**", "/openapi/**", "/vendor/**", "/styles/**", "/scripts/**", "/views/**", "/img/**", "/i18n/**", "/prefix-path").permitAll()
                .antMatchers("/**").hasAnyRole(USER_ROLE);
        http.formLogin().loginPage("/signin").defaultSuccessUrl("/", true).permitAll()
                .failureUrl("/signin?#/error").and()
//                .authenticationProvider(daoAuthenticationProvider())
                .httpBasic();
/*        http.logout().logoutUrl("/user/logout").invalidateHttpSession(true).clearAuthentication(true)
                .logoutSuccessUrl("/signin?#/logout");*/
        http.exceptionHandling().authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/signin"))
                //如果使用下面的authenticationEntryPoint，将不会再转跳到Apollo的登录页面，而是直接转跳到蜂巢的登录页面
//        http.exceptionHandling().authenticationEntryPoint(casAuthenticationEntryPoint())
                .and()
                .addFilter(casAuthenticationFilter())
                .addFilterBefore(casLogoutFilter(), LogoutFilter.class)
                .addFilterBefore(singleSignOutFilter(), CasAuthenticationFilter.class);
    }

    /**
     * 认证的入口，即跳转至服务端的cas地址
     * Note:浏览器访问不可直接填客户端的login请求,若如此则会返回Error页面，无法被此入口拦截
     */
    @Bean
    public CasAuthenticationEntryPoint casAuthenticationEntryPoint() {
        CasAuthenticationEntryPoint casAuthenticationEntryPoint = new CasAuthenticationEntryPoint();
        //Cas Server的登录地址
        String loginUrl = portalConfig.casServerName() + portalConfig.casServerUrlPrefix() + portalConfig.casServerLoginUrl();
        casAuthenticationEntryPoint.setLoginUrl(loginUrl);
        //service相关的属性
        casAuthenticationEntryPoint.setServiceProperties(serviceProperties());
        return casAuthenticationEntryPoint;
    }

    /**
     * 指定service相关信息
     * 设置客户端service的属性
     * 主要设置请求cas服务端后的回调路径,一般为主页地址，不可为登录地址
     */
    @Bean
    public ServiceProperties serviceProperties() {
        ServiceProperties serviceProperties = new ServiceProperties();
        //设置回调的service路径，此为主页路径
        //Cas Server认证成功后的跳转地址，这里要跳转到我们的Spring Security应用，
        //之后会由CasAuthenticationFilter处理，默认处理地址为/j_spring_cas_security_check
        serviceProperties.setService(portalConfig.portalClientName() + CAS_FILTER_PROCESSES_URL);
        // 对所有的未拥有ticket的访问均需要验证
        serviceProperties.setAuthenticateAllArtifacts(true);
        return serviceProperties;
    }

    /**
     * CAS认证过滤器
     */
    @Bean
    public CasAuthenticationFilter casAuthenticationFilter() throws Exception {
        CasAuthenticationFilter casAuthenticationFilter = new CasAuthenticationFilter();
        casAuthenticationFilter.setAuthenticationManager(authenticationManager());
        //指定处理地址，不指定时默认将会是“/j_spring_cas_security_check”
        casAuthenticationFilter.setFilterProcessesUrl(CAS_FILTER_PROCESSES_URL);
        return casAuthenticationFilter;
    }

    /**
     * 创建CAS校验类
     * Notes:TicketValidator、AuthenticationUserDetailService属性必须设置;
     * serviceProperties属性主要应用于ticketValidator用于去cas服务端检验ticket
     *
     * @return
     */
    @Bean
    public CasAuthenticationProvider casAuthenticationProvider() {
        CasAuthenticationProvider casAuthenticationProvider = new CasAuthenticationProvider();
        casAuthenticationProvider.setAuthenticationUserDetailsService(customerUserDetailsService());
        casAuthenticationProvider.setServiceProperties(serviceProperties());
        casAuthenticationProvider.setTicketValidator(cas20ServiceTicketValidator());
        casAuthenticationProvider.setKey("casAuthenticationProviderKey");
        return casAuthenticationProvider;
    }

    @Bean
    public ZtsUserDetailsService customerUserDetailsService() {
        return new ZtsUserDetailsService();
    }

    @Bean
    public RabcUserService rabcUserService() {
        return new RabcUserService();
    }

    /**
     * 配置Ticket校验器
     *
     * @return
     */
    @Bean
    public Cas20ServiceTicketValidator cas20ServiceTicketValidator() {
        // 配置上服务端的校验ticket地址
        return new Cas20ServiceTicketValidator(portalConfig.casServerName() + portalConfig.casServerUrlPrefix());
    }


    /**
     * 单点注销，接受cas服务端发出的注销session请求
     * SingleLogout(SLO) Front or Back Channel
     *
     * @return
     */
    @Bean
    public SingleSignOutFilter singleSignOutFilter() {
        SingleSignOutFilter singleSignOutFilter = new SingleSignOutFilter();
        singleSignOutFilter.setCasServerUrlPrefix(portalConfig.casServerName());
        singleSignOutFilter.setIgnoreInitConfiguration(true);
        return singleSignOutFilter;
    }

    /**
     * 单点请求CAS客户端退出Filter类
     * 请求/logout，转发至CAS服务端进行注销
     */
    @Bean
    public LogoutFilter casLogoutFilter() {
        // 设置回调地址，以免注销后页面不再跳转
        String logoutSuccessUrl = portalConfig.casServerName() + portalConfig.casServerUrlPrefix() + portalConfig.casServerLogoutUrl();
        String referrer = "?service=" + portalConfig.portalClientName() + "/signin?#/logout";

        SimpleUrlLogoutSuccessHandler urlLogoutSuccessHandler = new SimpleUrlLogoutSuccessHandler();
        urlLogoutSuccessHandler.setDefaultTargetUrl(logoutSuccessUrl + referrer);
        urlLogoutSuccessHandler.setUseReferer(false);

        LogoutFilter logoutFilter = new LogoutFilter(urlLogoutSuccessHandler, new SecurityContextLogoutHandler());
        logoutFilter.setFilterProcessesUrl(PORTAL_LOGOUT_URL);
        return logoutFilter;
    }

    @Bean
    public ZtsUserInfoHolder ztsUserInfoHolder() {
        return new ZtsUserInfoHolder();
    }

    @Bean
    public ZtsSsoHeartbeatHandler ztsSsoHeartbeatHandler() {
        return new ZtsSsoHeartbeatHandler();
    }

    @Bean
    public ZtsLogoutHandler ztsLogoutHandler() {
        return new ZtsLogoutHandler();
    }

    /**
     * 直接使用JdbcUserDetailsManager简化操作
     *
     * @param auth
     * @param datasource
     * @return JdbcUserDetailsManager
     * @throws Exception
     */
    @Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager(AuthenticationManagerBuilder auth,
                                                         DataSource datasource) throws Exception {
        JdbcUserDetailsManager jdbcUserDetailsManager = auth.jdbcAuthentication()
                .passwordEncoder(new BCryptPasswordEncoder()).dataSource(datasource)
                .usersByUsernameQuery("select Username,Password,Enabled from `Users` where Username = ?")
                .authoritiesByUsernameQuery(
                        "select Username,Authority from `Authorities` where Username = ?")
                .getUserDetailsService();

        jdbcUserDetailsManager.setUserExistsSql("select Username from `Users` where Username = ?");
        jdbcUserDetailsManager
                .setCreateUserSql("insert into `Users` (Username, Password, Enabled) values (?,?,?)");
        jdbcUserDetailsManager
                .setUpdateUserSql("update `Users` set Password = ?, Enabled = ? where id = (select u.id from (select id from `Users` where Username = ?) as u)");
        jdbcUserDetailsManager.setDeleteUserSql("delete from `Users` where id = (select u.id from (select id from `Users` where Username = ?) as u)");
        jdbcUserDetailsManager
                .setCreateAuthoritySql("insert into `Authorities` (Username, Authority) values (?,?)");
        jdbcUserDetailsManager
                .setDeleteUserAuthoritiesSql("delete from `Authorities` where id in (select a.id from (select id from `Authorities` where Username = ?) as a)");
        jdbcUserDetailsManager
                .setChangePasswordSql("update `Users` set Password = ? where id = (select u.id from (select id from `Users` where Username = ?) as u)");

        return jdbcUserDetailsManager;
    }
}