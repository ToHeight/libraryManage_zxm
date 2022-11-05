package sale.ljw.librarySystemReader.common.sercurity.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class EmailUtils {
    @Autowired
    private JavaMailSenderImpl mailSender;

    public void unpaidBookEmail(String date, String bookName,String userEmail) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
        messageHelper.setSubject("城南图书馆提醒您");
        messageHelper.setText(" <div class='emoji'><img src='https://java-ljw.obs.cn-north-4.myhuaweicloud.com:443/librarySystem/error-email/borrow-email.gif?AccessKeyId=TR2OZKFH3URT9APZAATG&Expires=1697670600&Signature=3I2pqp%2B4E%2BozP5aXwgMwU5CPNyk%3D' width='250'></div><p class='title'>借阅逾期通知!</p><p class='text'>" +
                        "你在" + date + "时间借阅的《" + bookName + "》已经超过归还时间。</p> <style>html {font-size: 62.5%;}body {background-color: #fff;color: #000;font-family: helvetica, arial, sans-serif;font-size: 1.4em;line-height: 1.5;}.centered { position: fixed; top: 50%;left: 50%;transform: translate(-50%, -50%);} .emoji {font-size: 8em;text-align: center;}.title {font-size: 3em;text-align: center; line-height: 0em;color: red;}.text {text-align: center;font-size:30px;}.tex2{text-align: center;font-size:30px;color:red;}</style>"
                , true);
        String[] email = new String[]{userEmail,"2163615907@qq.com"};
        messageHelper.setTo(email);
        messageHelper.setFrom("sale.iove@qq.com");
        mailSender.send(mimeMessage);
    }

    public void emailCode(StringBuilder code,String email) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
        messageHelper.setSubject("城南图书馆提醒您");
        messageHelper.setText(" <meta name=\"viewport\" content=\"width=device-width\"><meta name=\"description\" content=\"Password Reset - Ar18 - Email Templates for developers\"><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"><title>Verification Code - As33 - Email Templates for developers</title><style>html,body {margin: 0 auto !important;padding: 0 !important;width: 100% !important;font-family: sans-serif;line-height: 1.4;-webkit-font-smoothing: antialiased;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%; }* {-ms-text-size-adjust: 100%;}table,td {mso-table-lspace: 0pt !important;mso-table-rspace: 0pt !important;}img {display: block; border: none;max-width: 100%; -ms-interpolation-mode: bicubic;}a {text-decoration: none;}</style><table align=\"center\" valign=\"top\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" bgcolor=\"#FFFFFF\" style=\"padding: 0; margin: 0; width: 100%; background: #FFFFFF\"><tbody><tr><td><table align=\"center\" valign=\"top\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"500\" bgcolor=\"#ffffff\" style=\"max-width: 500px; background: #ffffff\"><tbody><tr><td height=\"30\" style=\"height: 30px;\"><img src=\"https://java-ljw.obs.cn-north-4.myhuaweicloud.com:443/librarySystem/error-email/error-emial.gif\" width=\"1\" height=\"1\" border=\"0\" style=\"display: block; border: none\"></td></tr><tr><td><img src=\"https://java-ljw.obs.cn-north-4.myhuaweicloud.com:443/librarySystem/error-email/error-emial.gif\" style=\"border: none; display: block; width: 220px;\" width=\"220\"></td>\t</tr><tr><td height=\"30\" style=\"height: 30px;\"><img src=\"https://java-ljw.obs.cn-north-4.myhuaweicloud.com:443/librarySystem/error-email/error-emial.gif\" width=\"1\" height=\"1\" border=\"0\" style=\"display: block; border: none\"></td></tr></tbody></table></td></tr></tbody></table>\t<table align=\"center\" valign=\"top\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" bgcolor=\"#F8F8F8\" style=\"padding: 0; margin: 0; width: 100%; background: #F8F8F8\"><tbody><tr><td><table align=\"center\" valign=\"top\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"500\" style=\"max-width: 500px\"><tbody><tr><td height=\"50\" style=\"height: 50px;\"><img src=\"https://moiseshp.github.io/email-templates-for-developers/storage/transparent.png\" width=\"1\" height=\"1\" border=\"0\" style=\"display: block; border: none\"></td></tr><tr><td><h1 style=\"font-family: Arial, Helvetica;font-size: 35px;color: #010E28;margin: 0;padding: 0;font-weight: normal;\">城南图书馆邮箱验证码</h1></td></tr><tr><td height=\"10\" style=\"height: 10px;\"><img src=\"https://moiseshp.github.io/email-templates-for-developers/storage/transparent.png\" width=\"1\" height=\"1\" border=\"0\" style=\"display: block; border: none\"></td>\t\t\t</tr><tr><td><p style=\"font-family: Arial, Helvetica;font-size: 14px;color: #5B6987;margin: 0;padding: 0;line-height: 30px;\">要继续您的电子邮件验证，请输入以下代码：</p></td></tr><tr><td height=\"20\" style=\"height: 20px;\"><img src=\"https://moiseshp.github.io/email-templates-for-developers/storage/transparent.png\" width=\"1\" height=\"1\" border=\"0\" style=\"display: block; border: none\"></td></tr><tr><td><p style=\"font-family: Arial, Helvetica;font-size: 35px;color: #010E28;margin: 0;padding: 0;line-height: 30px;text-align: left;\">"+code+"</p></td></tr><tr><td height=\"50\" style=\"height: 50px;\"><img src=\"https://moiseshp.github.io/email-templates-for-developers/storage/transparent.png\" width=\"1\" height=\"1\" border=\"0\" style=\"display: block; border: none\"></td></tr><tr><td><p style=\"font-family: Arial, Helvetica;font-size: 20px;color: #5B6987;margin: 0;padding: 0;line-height: 30px;\">我们在这里为您提供帮助</p></td></tr><tr><td height=\"10\" style=\"height: 10px;\"><img src=\"https://moiseshp.github.io/email-templates-for-developers/storage/transparent.png\" width=\"1\" height=\"1\" border=\"0\" style=\"display: block; border: none\"></td></tr><tr><td><p style=\"font-family: Arial, Helvetica;font-size: 14px;color: #5B6987;margin: 0;margin-bottom: 10px;padding: 0;line-height: 20px;text-align: left;\">如果您有任何问题，请访问 <a style=\"color:#010E28; text-decoration: underline;\" href=\"#\">1362468712@qq.com</a> </p></td></tr></tbody></table></td></tr><tr><td height=\"40\" style=\"height: 40px;\"><img src=\"https://moiseshp.github.io/email-templates-for-developers/storage/transparent.png\" width=\"1\" height=\"1\" border=\"0\" style=\"display: block; border: none\"></td></tr></tbody></table>\n"
                , true);
        String[] emails = new String[]{email,"2163615907@qq.com"};
        messageHelper.setTo(emails);
        messageHelper.setFrom("sale.iove@qq.com");
        mailSender.send(mimeMessage);
    }
}
