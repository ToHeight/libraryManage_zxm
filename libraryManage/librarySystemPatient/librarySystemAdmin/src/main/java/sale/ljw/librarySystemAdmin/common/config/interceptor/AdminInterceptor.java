package sale.ljw.librarySystemAdmin.common.config.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import sale.ljw.common.utils.JwtUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AdminInterceptor implements HandlerInterceptor {

    @Autowired
    private PermissionCheck permissionCheck;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //设置处理前方法
        //获取token、获取token中的权限id
        String permission = JwtUtils.parsePermission(request.getHeader("token"));
        if (permissionCheck.checkPermissions(request, response, handler)) {
            return HandlerInterceptor.super.preHandle(request, response, handler);
        }
        return false;
    }
}
