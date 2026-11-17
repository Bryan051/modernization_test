package com.example.sms.cmm.util;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * 메일 발송 유틸
 *
 * 2022.05.30 김철수  미수금 안내 메일 때문에 추가
 */
public class MailUtil {

    private static final String FROM_ADDR = "sms-noreply@example.com";

    private MailUtil() {
    }

    public static boolean send(String to, String subject, String body) {
        try {
            JavaMailSender mailSender = (JavaMailSender) SpringContextHolder.getBean("mailSender");
            MimeMessage msg = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg, false, "UTF-8");
            helper.setFrom(FROM_ADDR);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, false);
            mailSender.send(msg);
            return true;
        } catch (Exception e) {
            // TODO: 발송 실패 이력 남기기
            e.printStackTrace();
            return false;
        }
    }
}
