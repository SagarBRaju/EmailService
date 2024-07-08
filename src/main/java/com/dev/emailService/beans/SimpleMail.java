package com.dev.emailService.beans;

import jakarta.validation.constraints.*;

public class SimpleMail {

    @NotBlank(message = "Please provide valid email")
    private String to;

    @NotBlank(message = "Please provide subject for the email")
    private String subject;

    @NotBlank(message = "please provide message for the email")
    private String message;

    public SimpleMail(String to, String subject, String message) {
        this.to = to;
        this.subject = subject;
        this.message = message;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
