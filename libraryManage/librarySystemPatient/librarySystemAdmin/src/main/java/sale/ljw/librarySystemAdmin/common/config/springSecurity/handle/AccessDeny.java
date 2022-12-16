package sale.ljw.librarySystemAdmin.common.config.springSecurity.handle;


import com.alibaba.fastjson.JSON;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.common.common.http.StatusCode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>application name：librarySystemPatient - AccessDeny</p>
 * <p>application describing： </p>
 * <p>copyright： </p>
 * <p>company： </p>
 * <p>time：2022-12-14 16:33:18</p>
 *
 * @author liujingwen
 * @version ver 1.0
 * @since 1.8
 */
@Component
public class AccessDeny implements AccessDeniedHandler {


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(403);
        response.getWriter().write(JSON.toJSONString(ResponseResult.getErrorResult("Insufficient permissions require re-login", StatusCode.FORBIDDEN, null)));
    }


}
