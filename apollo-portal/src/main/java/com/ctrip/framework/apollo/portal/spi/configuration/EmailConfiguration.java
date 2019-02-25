package com.ctrip.framework.apollo.portal.spi.configuration;


import com.ctrip.framework.apollo.common.condition.ConditionalOnMissingProfile;
import com.ctrip.framework.apollo.portal.spi.EmailService;
import com.ctrip.framework.apollo.portal.spi.ctrip.CtripEmailRequestBuilder;
import com.ctrip.framework.apollo.portal.spi.ctrip.CtripEmailService;
import com.ctrip.framework.apollo.portal.spi.defaultimpl.DefaultEmailService;
import com.ctrip.framework.apollo.portal.spi.zts.ZtsEmailService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class EmailConfiguration {

    /**
     * spring.profiles.active = ctrip
     */
    @Configuration
    @Profile("ctrip")
    public static class CtripEmailConfiguration {

        @Bean
        public EmailService ctripEmailService() {
            return new CtripEmailService();
        }

        @Bean
        public CtripEmailRequestBuilder emailRequestBuilder() {
            return new CtripEmailRequestBuilder();
        }
    }

    /**
     * spring.profiles.active != ctrip
     */
    @Configuration
    @ConditionalOnMissingProfile({"ctrip", "zts"})
    public static class DefaultEmailConfiguration {
        @Bean
        @ConditionalOnMissingBean(EmailService.class)
        public EmailService defaultEmailService() {
            return new DefaultEmailService();
        }
    }

    /**
     * spring.profiles.active = zts
     */
    @Configuration
    @Profile("zts")
    public static class ZtsEmailConfiguration {

        @Bean(name = "threadPoolTaskExecutor")
        public Executor threadPoolTaskExecutor() {
            ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
            threadPoolTaskExecutor.setCorePoolSize(2);
            threadPoolTaskExecutor.setMaxPoolSize(10);
            threadPoolTaskExecutor.setQueueCapacity(200);
            threadPoolTaskExecutor.setKeepAliveSeconds(300);
            threadPoolTaskExecutor.setThreadGroupName("Apollo-Email-ThreadPool");
            threadPoolTaskExecutor.setThreadNamePrefix("Apollo-Email-Thread/");
            threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
            threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
            return threadPoolTaskExecutor;
        }

        @Bean
        public EmailService ztsEmailService() {
            return new ZtsEmailService();
        }
    }
}

