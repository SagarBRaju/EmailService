package com.dev.emailService.service;

import com.dev.emailService.beans.SimpleMail;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface EmailService {

    Map<String, Object> sendEmail(SimpleMail simpleMail);

    Map<String, Object> sendMultipleEmail(List<SimpleMail> simpleMails);

    Map<String, Object> sendEmailWithHtml(SimpleMail simpleMail);

    Map<String, Object> sendEmailWithFile(String to, String subject, String message, InputStream inputStream);
}
