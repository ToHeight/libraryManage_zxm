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

    public void unpaidBookEmail(String date, String bookName) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
        messageHelper.setSubject("城南图书馆提醒您");
        messageHelper.setText(" <div class='emoji'><img src='https://java-ljw.obs.cn-north-4.myhuaweicloud.com:443/librarySystem/error-email/error-emial.gif?AccessKeyId=TR2OZKFH3URT9APZAATG&amp;Expires=1697414862&amp;Signature=4OTkK1zs/RTfavUwEdhFA2FDgSU%3D' width='250'></div><p class='title'>借阅逾期通知!</p><p class='text'>" +
                        "你在" + date + "时间借阅的《" + bookName + "》已经超过归还时间。</p> <style>html {font-size: 62.5%;}body {background-color: #fff;color: #000;font-family: helvetica, arial, sans-serif;font-size: 1.4em;line-height: 1.5;}.centered { position: fixed; top: 50%;left: 50%;transform: translate(-50%, -50%);} .emoji {font-size: 8em;text-align: center;}.title {font-size: 3em;text-align: center; line-height: 0em;color: red;}.text {text-align: center;font-size:30px;}.tex2{text-align: center;font-size:30px;color:red;}</style>"
                , true);
        String[] email = new String[]{"1362468712@qq.com","2163615907@qq.com"};
        messageHelper.setTo(email);
        messageHelper.setFrom("sale.iove@qq.com");
        mailSender.send(mimeMessage);
    }
}
