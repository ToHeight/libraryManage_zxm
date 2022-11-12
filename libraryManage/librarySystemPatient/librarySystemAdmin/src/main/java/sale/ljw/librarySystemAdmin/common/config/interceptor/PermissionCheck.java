package sale.ljw.librarySystemAdmin.common.config.interceptor;

import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sale.ljw.common.utils.JwtUtils;
import sale.ljw.librarySystemAdmin.backend.service.ActionServiceAdmin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class PermissionCheck {

    @Autowired
    private ActionServiceAdmin actionServiceAdmin;
    public Boolean checkPermissions(HttpServletRequest request, HttpServletResponse response, Object handler){
        //设置处理前方法
        //获取token、获取token中的权限id
        String permission = JwtUtils.parsePermission(request.getHeader("token"));
        //查询permiession的全部模块权限
        String requestURI = request.getRequestURI();
        if(requestURI.split("/").length>4){
            requestURI = requestURI.substring(0, requestURI.indexOf("/",requestURI.indexOf("/", requestURI.indexOf("/",1)+1)+1));
        }
        if(actionServiceAdmin.checkPermissions(requestURI,permission)==0){
            try {
                loginFailureResponse(response);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return false;
        }
        return true;
    }
    private void loginFailureResponse(HttpServletResponse response) throws Exception {
        response.setStatus(401);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"当前权限\":\"不可访问\"}");
        response.getWriter().flush();
    }
}
