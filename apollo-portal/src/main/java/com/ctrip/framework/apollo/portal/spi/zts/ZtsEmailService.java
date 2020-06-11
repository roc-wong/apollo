package com.ctrip.framework.apollo.portal.spi.zts;

import com.ctrip.framework.apollo.portal.component.config.PortalConfig;
import com.ctrip.framework.apollo.portal.entity.bo.Email;
import com.ctrip.framework.apollo.portal.spi.EmailService;
import com.ctrip.framework.apollo.tracer.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;

import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Objects;

/**
 * 使用QQ邮箱进行邮件发送
 *
 * @author roc
 * @date 2020/6/11 17:22
 */
public class ZtsEmailService implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(ZtsEmailService.class);

    @Autowired
    private PortalConfig portalConfig;
    @Autowired
    private JavaMailSender javaMailSender;


    @Override
    public void send(Email email) {

        try {
            if (portalConfig.isSendEmailAsync()) {
                sendEmailAsync(email);
            } else {
                sendHtmlMail(email);
            }
        } catch (Throwable e) {
            logger.error("send email failed", e);
            Tracer.logError("send email failed", e);
        }
    }


    @Async
    public void sendEmailAsync(Email email) {
        sendHtmlMail(email);
    }


    private void sendHtmlMail(Email email) {
        try {
            logger.info("Current thread is {}", Thread.currentThread());
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
            if (Objects.nonNull(email.getRecipients())) {
                mimeMessageHelper.setTo(email.getRecipients().toArray(new String[email.getRecipients().size()]));
            }
            mimeMessageHelper.setReplyTo(email.getSenderEmailAddress());
            mimeMessageHelper.setFrom(email.getSenderEmailAddress());
            mimeMessageHelper.setSubject(email.getSubject());
            mimeMessageHelper.setText(email.getBody(), true);
            mimeMessageHelper.setSentDate(new Date());
            javaMailSender.send(message);
        } catch (javax.mail.MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
