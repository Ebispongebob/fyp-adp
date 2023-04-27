package com.fyp.adp.dispatcher.sender;

import com.fyp.adp.common.utils.EmailUtil;
import com.fyp.adp.dispatcher.constant.EmailTemplate;
import com.fyp.adp.dispatcher.constant.PlaceholderConstants;
import com.fyp.adp.dispatcher.entity.CommonMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.MessagingException;

public class EmailSender implements Sender {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    String title;
    String message;
    String url;

    public EmailSender(String url, CommonMessage commonMessage) {
        this.url = url;
        init(commonMessage);
    }

    private void init(CommonMessage commonMessage) {
        title = commonMessage.getTitle();
        message = EmailTemplate.emailTemplate
                .replaceAll(PlaceholderConstants.message, commonMessage.getMessage())
                .replaceAll(PlaceholderConstants.reference, commonMessage.getReference())
                .replaceAll(PlaceholderConstants.eventTime, commonMessage.getEventTime());
    }

    @Override
    public void send() {
        try {
            EmailUtil.sendEmail(url, title, message);
        } catch (MessagingException e) {
            logger.error("Failed to send email: {}", e.getMessage());
        }
    }
}
