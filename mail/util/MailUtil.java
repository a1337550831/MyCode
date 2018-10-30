package com.sun.mail.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;


@Component
public class MailUtil {
    @Value("${spring.mail.username}")
    private String from;

    @Resource
    private JavaMailSender mailSender;

    private final Logger log = LoggerFactory.getLogger(MailUtil.class);

    //发送简单邮件
    public void senderMail(String alias, String to, String subject, String content) {
        log.info("发送普通邮件：{},{},{},{}",alias,to,subject,content);
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(alias + "<" + from + ">");
            message.setTo(to);
            message.setSubject(subject);
            message.setText(content);
            mailSender.send(message);
            log.info("发送普通邮件成功");
        }catch (Exception e){
            e.printStackTrace();
            log.info("发送普通邮件失败："+e.getMessage());
        }
    }

    //发送html文件
    public void senderHtmlMail(String alias, String to, String subject, String content) {

        log.info("发送html邮件：{},{},{},{}",alias,to,subject,content);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = null;
        try {
            messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setFrom(alias + "<" + from + ">");
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(content, true);
            mailSender.send(message);
            log.info("发送html邮件成功");
        } catch (MessagingException e) {
            e.printStackTrace();
            log.info("发送html文件失败："+e.getMessage());
        }
    }

    //发送带有附件的邮件
    public void sendAttachmentsMail(String alias, String to, String subject, String content, String filePath)  {
        log.info("发送html邮件：{},{},{},{},{}",alias,to,subject,content,filePath);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = null;
        try {
            messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setFrom(alias + "<" + from + ">");
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(content, true);
            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = file.getFilename();
            messageHelper.addAttachment(fileName, file);
            mailSender.send(message);
            log.info("发送带附件的邮件成功");
        } catch (MessagingException e) {
            e.printStackTrace();
            log.info("发送带附件的邮件失败："+e.getMessage());
        }
    }

    //发送HTML静态图片
    public void sendInlinResourceMail(String alias, String to, String subject, String content,
                                      String rscPath,String rscId){
        log.info("发送html邮件：{},{},{},{},{},{}",alias,to,subject,content,rscPath,rscId);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = null;
        try {
            messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setFrom(alias + "<" + from + ">");
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(content, true);
            FileSystemResource res = new FileSystemResource(new File(rscPath));
            messageHelper.addInline(rscId,res);
            mailSender.send(message);
            log.info("发送静态图片邮件成功");
        } catch (MessagingException e) {
            e.printStackTrace();
            log.info("发送静态图片邮件失败："+e.getMessage());
        }
    }

//    @Test
//    public void testInlineMail() throws MessagingException {
//        String imgPath = "C:\\Users\\root\\Desktop\\images.jpg";
//        String rscId = "img001";
//        String html = "<html>\n" +
//                "<body>\n" +
//                "<h1>这是一张图片</h1>\n" +
//                "<img src=\'cid:"+rscId+"\'>\n" +
//                "</body>\n" +
//                "</html>";
//        mailUtil.sendInlinResourceMail("Gift","1337550831@qq.com",
//                "Come On",html,imgPath,rscId);
//    }


}
