package com.fyp.adp.common.utils;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailUtil {

    public static void sendEmail(String to, String subject, String body) throws MessagingException {

        // 发件人邮箱地址和密码
        String from = "13067309843@163.com";
        String password = "ESZSLCCBTDIGNALW";

        // SMTP邮件服务器地址和端口
        String smtpHost = "smtp.163.com";
        int smtpPort = 465;

        // 邮件会话属性
        Properties props = new Properties();
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");

        // 创建邮件会话
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        // 创建邮件消息
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(body);

        // 发送邮件
        Transport.send(message);
    }
}
