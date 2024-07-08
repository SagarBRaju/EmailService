package com.dev.emailService.service;

import com.dev.emailService.beans.SimpleMail;
import com.dev.emailService.exception.CustomException;
import com.dev.emailService.helper.BasicResponse;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.username}")
    String userName;

    @Autowired
    private JavaMailSender sender;

    private final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Override
    public Map<String, Object> sendEmail(SimpleMail simpleMail) {
        validateEmailDetails(simpleMail);
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(userName);
        simpleMailMessage.setTo(simpleMail.getTo());
        simpleMailMessage.setSubject(simpleMail.getSubject());
        simpleMailMessage.setText(simpleMail.getMessage());
        sender.send(simpleMailMessage);
        logger.info("Email successfully sent to : " + simpleMail.getTo());
        return new BasicResponse("Email sent successfully", null).success();
    }

    @Override
    public Map<String, Object> sendMultipleEmail(List<SimpleMail> simpleMails) {
        for (SimpleMail simpleMail : simpleMails) {
            validateEmailDetails(simpleMail);
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(userName);
            simpleMailMessage.setTo(simpleMail.getTo());
            simpleMailMessage.setSubject(simpleMail.getSubject());
            simpleMailMessage.setText(simpleMail.getMessage());
            sender.send(simpleMailMessage);
            logger.info("Email successfully sent to : " + simpleMail.getTo());
        }
        return new BasicResponse("Email sent successfully", null).success();
    }

    @Override
    public Map<String, Object> sendEmailWithHtml(SimpleMail simpleMail) {
        validateEmailDetails(simpleMail);
        MimeMessage simpleMailMessage = sender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(simpleMailMessage, true, "UTF-8");
            mimeMessageHelper.setFrom(userName);
            mimeMessageHelper.setTo(simpleMail.getTo());
            mimeMessageHelper.setSubject(simpleMail.getSubject());
            mimeMessageHelper.setText(simpleMail.getMessage(), true);
            sender.send(simpleMailMessage);
            logger.info("Email successfully sent to : " + simpleMail.getTo());
            return new BasicResponse("Email sent successfully", null).success();
        } catch (MessagingException e) {
            throw new CustomException(801, e.getLocalizedMessage());
        }
    }

    @Override
    public Map<String, Object> sendEmailWithFile(String to, String subject, String message, InputStream inputStream) {
        SimpleMail simpleMail = new SimpleMail(to, subject, message);
        validateEmailDetails(simpleMail);
        try {
            File file = getFile(inputStream);
            if (file.length() == 0) {
                throw new CustomException(801, "Please provide file");
            }
            MimeMessage mimeMessage = sender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(message);
            mimeMessageHelper.addAttachment(file.getName(), file);
            sender.send(mimeMessage);
            logger.info("Email successfully sent to : " + to);
            return new BasicResponse("Email sent successfully", null).success();
        } catch (MessagingException e) {
            throw new CustomException(801, e.getLocalizedMessage());
        }
    }

    public File getFile(InputStream inputStream) {
        File file = new File("src/main/resources/images/information.jpg");
        try {
            Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return file;
        } catch (IOException e) {
            throw new CustomException(801, e.getLocalizedMessage());
        }
    }

    public void validateEmailDetails(SimpleMail simpleMail) {
        if (simpleMail.getTo() == null || Objects.equals(simpleMail.getTo(), "")) {
            throw new CustomException(801, "Please provide valid email");
        }
        if (simpleMail.getSubject() == null || Objects.equals(simpleMail.getSubject(), "")) {
            throw new CustomException(801, "Please provide subject for the email");
        }
        if (simpleMail.getMessage() == null || Objects.equals(simpleMail.getMessage(), "")) {
            throw new CustomException(801, "Please provide message for the email");
        }
    }
}
