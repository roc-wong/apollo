package com.ctrip.framework.apollo.portal.spi.zts;

import com.ctrip.framework.apollo.portal.component.config.PortalConfig;
import com.ctrip.framework.apollo.portal.entity.bo.Email;
import com.ctrip.framework.apollo.portal.spi.EmailService;
import com.ctrip.framework.apollo.tracer.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.lang.reflect.Method;


public class ZtsEmailService implements EmailService, ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(ZtsEmailService.class);

    @Autowired
    private ZtsEmailRequestBuilder emailRequestBuilder;
    @Autowired
    private PortalConfig portalConfig;

    private Object emailServiceClient;
    private Method sendEmailAsync;
    private Method sendEmail;

    public void init() {
        try {
            Class emailServiceClientClazz =
                    Class.forName("com.zts.framework.email.client.ZtsEmailClient");

            logger.info("emailServiceClientClazz = {}", emailServiceClientClazz);

            Method getInstanceMethod = emailServiceClientClazz.getMethod("getInstance");

            logger.info("getInstanceMethod = {}", getInstanceMethod);

            emailServiceClient = getInstanceMethod.invoke(null);

            Class sendEmailRequestClazz =
                    Class.forName("com.zts.framework.email.client.SendEmailRequest");
            sendEmailAsync = emailServiceClientClazz.getMethod("sendEmailAsync", sendEmailRequestClazz);
            sendEmail = emailServiceClientClazz.getMethod("sendEmailSync", sendEmailRequestClazz);
        } catch (Throwable e) {
            logger.error("init zts email service failed", e);
            Tracer.logError("init zts email service failed", e);
        }
    }


    @Override
    public void send(Email email) {

        try {
            Object emailRequest = emailRequestBuilder.buildEmailRequest(email);

            Object sendResponse = portalConfig.isSendEmailAsync() ?
                    sendEmailAsync.invoke(emailServiceClient, emailRequest) :
                    sendEmail.invoke(emailServiceClient, emailRequest);

            logger.info("Email server response: {}", sendResponse);

        } catch (Throwable e) {
            logger.error("send email failed", e);
            Tracer.logError("send email failed", e);
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {
            //root application context 没有parent，当spring容器初始化完成后就会执行该方法。
            init();
        }
    }
}
