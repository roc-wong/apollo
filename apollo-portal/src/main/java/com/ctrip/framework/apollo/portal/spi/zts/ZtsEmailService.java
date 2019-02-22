package com.ctrip.framework.apollo.portal.spi.zts;

import com.ctrip.framework.apollo.portal.component.config.PortalConfig;
import com.ctrip.framework.apollo.portal.entity.bo.Email;
import com.ctrip.framework.apollo.portal.spi.EmailService;
import com.ctrip.framework.apollo.tracer.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;


public class ZtsEmailService implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(ZtsEmailService.class);

    private Object emailServiceClient;
    private Method sendEmailAsync;
    private Method sendEmail;

    @Autowired
    private ZtsEmailRequestBuilder emailRequestBuilder;
    @Autowired
    private PortalConfig portalConfig;

    @PostConstruct
    public void init() {
        try {
//      initServiceClientConfig();

            Class emailServiceClientClazz =
                    Class.forName("com.zts.framework.email.client.ZtsEmailClient");

            logger.info("emailServiceClientClazz = {}", emailServiceClientClazz);

            Method getInstanceMethod = emailServiceClientClazz.getMethod("getInstance");

            logger.info("getInstanceMethod = {}", getInstanceMethod);

            emailServiceClient = getInstanceMethod.invoke(null);

            Class sendEmailRequestClazz =
                    Class.forName("com.zts.framework.email.client.SendEmailRequest");
            sendEmailAsync = emailServiceClientClazz.getMethod("sendEmailAsync", sendEmailRequestClazz);
            sendEmail = emailServiceClientClazz.getMethod("sendEmail", sendEmailRequestClazz);
        } catch (Throwable e) {
            logger.error("init zts email service failed", e);
            Tracer.logError("init zts email service failed", e);
        }
    }

 /* private void initServiceClientConfig() throws Exception {

    Class serviceClientConfigClazz = Class.forName("com.ctriposs.baiji.rpc.client.ServiceClientConfig");
    Object serviceClientConfig = serviceClientConfigClazz.newInstance();
    Method setFxConfigServiceUrlMethod = serviceClientConfigClazz.getMethod("setFxConfigServiceUrl", String.class);

    setFxConfigServiceUrlMethod.invoke(serviceClientConfig, portalConfig.soaServerAddress());

    Class serviceClientBaseClazz = Class.forName("com.ctriposs.baiji.rpc.client.ServiceClientBase");
    Method initializeMethod = serviceClientBaseClazz.getMethod("initialize", serviceClientConfigClazz);
    initializeMethod.invoke(null, serviceClientConfig);
  }*/

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

}
