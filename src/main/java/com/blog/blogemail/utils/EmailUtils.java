package com.blog.blogemail.utils;

import com.blog.base.emailApi.req.EmailContentEntity;
import com.blog.base.emailApi.req.EmailEnclosureDTO;
import com.blog.blogemail.dao.EmailServerDTO;
import com.blog.blogemail.mapper.EmailServerMapper;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.annotation.PostConstruct;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Slf4j
@Component
public class EmailUtils {

    @Autowired
    private EmailServerMapper emailServerMapper;

    private EmailServerDTO emailServerDTO;


    @PostConstruct
    private void initEmailServer() {
        emailServerDTO = emailServerMapper.selectById(1);
    }

    //发送邮件
    public boolean sendEmail(EmailContentEntity upEntity) {
        try {
            System.clearProperty("mail.host");
            System.clearProperty("mail.smtp.host");
            System.clearProperty("mail.smtp.ssl.enable");
            System.clearProperty("mail.transport.protocol");
            System.clearProperty("mail.smtp.starttls.enable");
            // 获取系统属性
            Properties properties = System.getProperties();
            // 添加properties信息
            properties.put("mail.smtp.auth", "true");
            properties.setProperty("mail.smtp.port", String.valueOf(emailServerDTO.getPort()));
            // 发送邮件协议名称
            if (emailServerDTO.getAgreement().equalsIgnoreCase("STARTTLS")) {
                properties.put("mail.smtp.starttls.enable", "true");
                properties.setProperty("mail.transport.protocol", "smtp");
                properties.setProperty("mail.host", emailServerDTO.getHost());
            } else if (emailServerDTO.getAgreement().equalsIgnoreCase("SSL")) {
                properties.put("mail.smtp.ssl.enable", "true");
                properties.setProperty("mail.smtp.host", emailServerDTO.getHost());
            }

            Session session = Session.getInstance(properties);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailServerDTO.getUserName()));
            List<String> emails = upEntity.getEmails();
            InternetAddress[] internetAddressTo = new InternetAddress[emails.size()];
            InternetAddress Address;
            for (int i = 0; i < emails.size(); i++) {
                Address = new InternetAddress();
                Address.setAddress(emails.get(i).replace(" ", ""));
                internetAddressTo[i] = Address;
            }
            message.addRecipients(Message.RecipientType.TO, internetAddressTo);
            // set cc ：抄送
            List<String> ccEmail = upEntity.getCcEmail();
            if (!CollectionUtils.isEmpty(ccEmail)) {
                InternetAddress[] internetAddressTCC = new InternetAddress[ccEmail.size()];
                InternetAddress internetAddress;
                for (int i = 0; i < ccEmail.size(); i++) {
                    internetAddress = new InternetAddress();
                    internetAddress.setAddress(ccEmail.get(i).replace(" ", ""));
                    internetAddressTCC[i] = internetAddress;
                }
                message.addRecipients(Message.RecipientType.CC, internetAddressTCC);
            }
            message.setSubject(upEntity.getTitle());

            //正文
            MimeBodyPart text = new MimeBodyPart();
            text.setContent(upEntity.getContent(), "text/html;charset=UTF-8");
            //添加附件
            List<EmailEnclosureDTO> enclosureList = upEntity.getEnclosureList();
            Multipart multipart = new MimeMultipart();//向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
            multipart.addBodyPart(text);
            try {

                for (EmailEnclosureDTO cloudEmailEnclosureDTO : enclosureList) {
                    //添加附件
                    BodyPart messageBodyPart = new MimeBodyPart();
                    String p = StringUtils.replace(cloudEmailEnclosureDTO.getEnclosure(), "\\", "/");
                    DataSource source = new FileDataSource(p);
                    messageBodyPart.setDataHandler(new DataHandler(source));
                    //添加附件的内容
                    messageBodyPart.setFileName(MimeUtility.encodeWord(cloudEmailEnclosureDTO.getEncName()));
                    multipart.addBodyPart(messageBodyPart);
                }
            } catch (Exception e) {
                log.error("SEND_EMAIL>>addBodyPart", e);
            }
            message.setContent(multipart);//将multipart对象放到message中
            message.setSentDate(new Date());
            Transport.send(message, emailServerDTO.getUserName(), DESUtil.getDecryptString(emailServerDTO.getPassword()));
            properties.clear();
            log.info(new Gson().toJson(upEntity.getTitle() + upEntity.getEmails()));
            return true;
        } catch (Exception e) {
            log.error("发送短信异常,{}", upEntity, e);
            return false;
        }
    }

}
