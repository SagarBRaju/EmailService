package com.dev.emailService.controller;

import com.dev.emailService.beans.SimpleMail;
import com.dev.emailService.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import java.io.IOException;
import java.util.List;

import static com.dev.emailService.helper.EmailConstants.*;

@RestController
@RequestMapping(MAIN_URL)
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping(SEND_SIMPLE_MAIL)
    public ResponseEntity<?> sendSimpleMail(@RequestBody SimpleMail simpleMail) {
        return ResponseEntity.ok().body(emailService.sendEmail(simpleMail));
    }

    @PostMapping(SEND_MULTIPLE_MAIL)
    public ResponseEntity<?> sendMultipleMail(@RequestBody List<SimpleMail> simpleMails) {
        return ResponseEntity.ok().body(emailService.sendMultipleEmail(simpleMails));
    }

    @PostMapping(SEND_HTML_MAIL)
    public ResponseEntity<?> sendMailWithHtml(@RequestBody SimpleMail simpleMail) {
        return ResponseEntity.ok().body(emailService.sendEmailWithHtml(simpleMail));
    }

    @PostMapping(SEND_MAIL_WITH_FILE)
    public ResponseEntity<?> sendMailWithFile(@RequestBody MultipartFile file, @RequestParam String to, @RequestParam String subject, @RequestParam String message) throws IOException {
        return ResponseEntity.ok().body(emailService.sendEmailWithFile(to, subject, message, file.getInputStream()));
    }
}
