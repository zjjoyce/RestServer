package com.asiainfo.ocmanager.mail;

/**
 * Created by yujin on 2017/7/11.
 */


import com.asiainfo.ocmanager.rest.resource.quotaUtils.quotaQuery;
import com.sun.mail.smtp.SMTPTransport;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.security.Security;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author yujing2
 *
 */
public class SendMail {

    public static void Send(String username, String password, String recipientEmail, String title, String message){

        try {
            username = ParamQuery.getCFProperties().get(ParamQuery.SEND_MAIL_USERNAME);
            password = ParamQuery.getCFProperties().get(ParamQuery.SEND_MAIL_PASSWORD);
            Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

            Properties props = System.getProperties();
            props.setProperty("mail.smtps.host", "mail.asiainfo.com");
            props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
            props.setProperty("mail.smtp.socketFactory.fallback", "false");
            props.setProperty("mail.smtp.port", "25");
            props.setProperty("mail.smtp.socketFactory.port", "25");
            props.setProperty("mail.smtps.auth", "true");

            props.put("mail.smtps.quitwait", "false");

            Session session = Session.getInstance(props, null);

            final MimeMessage msg = new MimeMessage(session);

            msg.setFrom(new InternetAddress(username));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail,false));

            /*if (ccEmail.length() > 0) {
                msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccEmail, false));
            }*/

            msg.setSubject(title);
            msg.setText(message, "utf-8");
            msg.setSentDate(new Date());

            SMTPTransport t = (SMTPTransport)session.getTransport("smtps");

            t.connect("mail.asiainfo.com", username, password);
            t.sendMessage(msg, msg.getAllRecipients());
            t.close();
            System.out.println(username + " " + password);
        } catch (Exception e) {
            System.out.println("SendMail Exception " + e.getMessage());
        }


    }
    public static void main(String[] args){
        /*String username = "yujing2@asiainfo.com";
        String passwd = "765@asiainfo";
        String recipientEmail = "yanls@asiainfo.com";

        String title = "test send email to myself";
        String message = "hello world";
        Send(username, passwd, recipientEmail, title, message);*/
        //Map quota = quotaQuery.getMongoQuota("50dadbf3-d3f5-11e6-bf26-00163e00009d");
        //Map quota = quotaQuery.getGpQuota("d778303ea916d266");
        //Map quota = quotaQuery.getKafkaQuota("__consumer_offsets");

    }
}
