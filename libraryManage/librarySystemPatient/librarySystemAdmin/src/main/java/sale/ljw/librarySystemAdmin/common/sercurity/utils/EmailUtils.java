package sale.ljw.librarySystemAdmin.common.sercurity.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class EmailUtils {
    @Autowired
    private JavaMailSenderImpl mailSender;
    @Value("${front-end-prefix}")
    private String prefix;

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
    public void deleteComments(String userName, String bookName,String userEmail,String commentsInfo,String deleteInfo) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
        messageHelper.setSubject("城南图书馆提醒您");
        messageHelper.setText("<meta charset='utf-8'><meta http-equiv='X-UA-Compatible' content='IE=edge'><meta name='viewport' content='width=device-width, initial-scale=1, shrink-to-fit=no'><meta name='description' content='Password Reset - Ac89 - Email Templates for developers'><meta http-equiv='Content-Type' content='text/html; charset=UTF-8'><title>留言删除提醒</title><style>html,body {margin: 0 auto !important;padding: 0 !important;width: 100% !important;font-family: sans-serif;line-height: 1.4;-webkit-font-smoothing: antialiased;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;}* {-ms-text-size-adjust: 100%;}table,td {mso-table-lspace: 0pt !important;mso-table-rspace: 0pt !important;}img {display: block;border: none;max-width: 100%;-ms-interpolation-mode: bicubic;}a {text-decoration: none;}</style><table role='presentation' align='center' valign='top' border='0' cellpadding='0' cellspacing='0' width='100%' style='border-spacing: 0;border-collapse: collapse;vertical-align: top;padding: 0;margin: 0;width: 100%;text-align: center;'><tbody><tr><td align='center' valign='top'><table role='presentation' align='center' border='0' cellpadding='0' cellspacing='0' width='600' bgcolor='#ffffff' style='max-width: 600px;border-spacing: 0;border-collapse: collapse;vertical-align: top;padding: 0;margin: 0;width: 100%;background: #ffffff;'><tbody><tr><td height='40&quot;' style='height:40px'><img src='https://java-ljw.obs.cn-north-4.myhuaweicloud.com:443/librarySystem/error-email/transparent.png' width='1' height='1' border='0' style='display: block; border: none'></td></tr><tr><td align='center' valign='top'><table role='presentation' align='center' border='0' cellpadding='0' cellspacing='0' width='440' style='max-width: 440px;border-spacing: 0;border-collapse: collapse;vertical-align: top;padding: 0;margin: 0;width: 100%;text-align: center;'><tbody><tr><td align='center'><img src='https://java-ljw.obs.cn-north-4.myhuaweicloud.com:443/librarySystem/error-email/email_1.gif' width='170' style='border: none; display: block; max-width: 170px; width: 100%'></td></tr><tr><td height='40' style='height:40px'><img src='https://java-ljw.obs.cn-north-4.myhuaweicloud.com:443/librarySystem/error-email/transparent.png' width='1' height='1' border='0' style='display: block; border: none'></td></tr><tr><td><h1 style='font-family: Arial, Helvetica, sans-serif;font-size: 24px;color: #010E28;font-weight: bold;margin: 0;margin-bottom: 5px;padding: 0'>您好，" +
                userName + ":"+
                "</h1></td></tr><tr><td height='15' style='height: 15px'><img src='https://java-ljw.obs.cn-north-4.myhuaweicloud.com:443/librarySystem/error-email/transparent.png' width='1' height='1' border='0' style='display: block; border: none'></td></tr><tr><td><p style='font-family: Arial, Helvetica, sans-serif;font-size: 15px;color: #5B6987;margin: 0;padding: 0;line-height: 20px;'>您在《" +
                bookName +
                "》书下留下的" +
                "'&nbsp;&nbsp;"+commentsInfo +
                "....&nbsp;&nbsp;'书评已被管理员删除</p></td></tr><tr><td height='30' style='height: 30px'><img src='https://java-ljw.obs.cn-north-4.myhuaweicloud.com:443/librarySystem/error-email/transparent.png' width='1' height='1' border='0' style='display: block; border: none'></td></tr><tr><td><p style='font-family: Arial, Helvetica, sans-serif;font-size: 15px;color: #5B6987;margin: 0;padding: 0;line-height: 20px;font-weight: bold;'>删除原因为：</p></td></tr><tr><td height='20' style='height: 20px'><img src='https://java-ljw.obs.cn-north-4.myhuaweicloud.com:443/librarySystem/error-email/transparent.png' width='1' height='1' border='0' style='display: block; border: none'></td></tr><tr><td><p style='font-family: Arial, Helvetica, sans-serif;font-size: 15px;color: #010E28;margin: 0;padding: 0;line-height: 20px;font-weight: bold;'>" +
                deleteInfo +
                "</p></td></tr><tr><td height='20' style='height: 20px'><img src='https://java-ljw.obs.cn-north-4.myhuaweicloud.com:443/librarySystem/error-email/transparent.png' width='1' height='1' border='0' style='display: block; border: none'></td></tr><tr><td><p style='font-family: Arial, Helvetica, sans-serif;font-size: 15px;color: #5B6987;margin: 0;padding: 0;line-height: 20px;font-weight: bold;'><font __editorwarp__='1' style='display: inline; font-size: 14px; font-family: sans-serif; color: rgb(0, 0, 0); background-color: rgb(255, 255, 255); font-weight: 400; font-style: normal;'>如果您有问题可以联系：2163615907@qq.com</font></p></td></tr></tbody></table></td></tr><tr><td height='40' style='height:40px'><img src='https://java-ljw.obs.cn-north-4.myhuaweicloud.com:443/librarySystem/error-email/transparent.png' width='1' height='1' border='0' style='display: block; border: none'></td></tr></tbody></table></td></tr></tbody></table>", true);
        String[] email = new String[]{userEmail,"2163615907@qq.com"};
        messageHelper.setTo(email);
        messageHelper.setFrom("sale.iove@qq.com");
        mailSender.send(mimeMessage);
    }
}
