package sale.ljw.librarySystemAdmin.common.config.interceptor;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.common.common.http.StatusCode;
import sale.ljw.common.utils.JwtUtils;
import sale.ljw.librarySystemAdmin.backend.service.ActionServiceAdmin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Component
public class PermissionCheck {

    @Autowired
    private ActionServiceAdmin actionServiceAdmin;
    @Autowired
    private JavaMailSenderImpl mailSender;

    public Boolean checkPermissions(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //设置处理前方法
        //获取token、获取token中的权限id
        String permission = JwtUtils.parsePermission(request.getHeader("token"));
        //查询permiession的全部模块权限
        String requestURI = request.getRequestURI();
        if (requestURI.split("/").length > 4) {
            requestURI = requestURI.substring(0, requestURI.indexOf("/", requestURI.indexOf("/", requestURI.indexOf("/", 1) + 1) + 1));
        }
        return actionServiceAdmin.checkPermissions(requestURI, permission) != 0;
    }

    @ResponseStatus(code = HttpStatus.NOT_IMPLEMENTED)
    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public String methodArgumentNotValidException(HttpServletRequest request, HttpServletResponse response, MethodArgumentNotValidException e) {
        //封装需要返回的错误信息
        Map<String, String> errorMsg = new HashMap<>(64);
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errorMsg.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return JSON.toJSONString(ResponseResult.getErrorResult(StatusCode.NOT_IMPLEMENTED, "501", errorMsg));
    }

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception e) {
        System.out.println(getExceptionAllInfo(e));
//        emailSending(e);
        return JSON.toJSONString(ResponseResult.getErrorResult(StatusCode.ERROR, "500", e.getMessage()));
    }

    public void emailSending(Exception e) {
        System.out.println(e.getMessage());
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        System.out.println("当前时间" + new Date());
        mailMessage.setSubject("服务器内部错误消息！请立即处理错误");
        mailMessage.setText("错误发生时间为" + new Date() + "，错误响应码为" + HttpStatus.SERVICE_UNAVAILABLE + "\r\n当前错误消息为:" + e.getMessage() + "\r\n请立即检查程序，防止服务器宕机。"
                + "\r\n此类消息由错误处理类自动处理如需要管理，请自行处理"
        );
        mailMessage.setTo("1362468712@qq.com");
        mailMessage.setFrom("sale.iove@qq.com");
        mailSender.send(mailMessage);
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
