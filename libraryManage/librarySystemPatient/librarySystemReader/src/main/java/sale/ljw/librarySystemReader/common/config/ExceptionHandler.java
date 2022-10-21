package sale.ljw.librarySystemReader.common.config;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.common.common.http.StatusCode;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 异常处理类
 */
@ControllerAdvice
@Component
public class ExceptionHandler {

    @Autowired
    private JavaMailSenderImpl mailSender;

    @ResponseStatus(code = HttpStatus.NOT_IMPLEMENTED)
    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public String methodArgumentNotValidException(HttpServletRequest request, HttpServletResponse response, MethodArgumentNotValidException e){
        //封装需要返回的错误信息
        Map<String, String> errorMsg = new HashMap<>(64);
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errorMsg.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return JSON.toJSONString(ResponseResult.getErrorResult(StatusCode.NOT_IMPLEMENTED, "501",errorMsg));
    }

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    @ResponseBody
    public String exceptionHandler(Exception e) throws MessagingException {
        System.out.println(getExceptionAllInfo(e));
        emailSending(e);
        return JSON.toJSONString(ResponseResult.getErrorResult(StatusCode.ERROR, "500", e.getMessage()));
    }

    public void emailSending(Exception e) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,true);
        messageHelper.setSubject("服务器内部错误消息！请立即处理错误");
        messageHelper.setText("<style type='text/css'>html {font-size: 62.5%;}body {background-color: #fff;color: #000;font-family: helvetica, arial, sans-serif;font-size: 1.4em;line-height: 1.5; }.centered {position: fixed;top: 50%;left: 50%;transform: translate(-50%, -50%);}.emoji {font-size: 9em;text-align: center;}.title {font-size: 3em;text-align: center;line-height: 0em;color: grey;}.text {text-align: center;}</style>" +
                        "<div class='emoji'><img src='https://java-ljw.obs.cn-north-4.myhuaweicloud.com:443/librarySystem/error-email/error-emial.gif?AccessKeyId=TR2OZKFH3URT9APZAATG&amp;Expires=1697414862&amp;Signature=4OTkK1zs/RTfavUwEdhFA2FDgSU%3D' width='250'></div><p class='title'>" +
                        "服务器出错！</p><p class='text'>" +
                        "错误信息邮件提醒 \uD83D\uDE40<br>" +
                        "错误发生时间为：" +DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss")+
                        "<br>服务器出错错误信息如下：</p><p>"+getExceptionAllInfo(e)+"</p>"
        ,true);
        messageHelper.setTo("1362468712@qq.com");
        messageHelper.setFrom("sale.iove@qq.com");
        mailSender.send(mimeMessage);
    }


    public String getExceptionAllInfo(Exception ex) {
        ByteArrayOutputStream out = null;
        PrintStream pout = null;
        String ret = "";
        try {
            out = new ByteArrayOutputStream();
            pout = new PrintStream(out);
            ex.printStackTrace(pout);
            ret = new String(out.toByteArray());
            out.close();
        } catch (Exception e) {
            return ex.getMessage();
        } finally {
            if (pout != null) {
                pout.close();
            }
        }
        return ret;
    }
    }
