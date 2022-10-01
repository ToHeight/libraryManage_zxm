package sale.ljw.librarySystemAdmin.common.sercurity.intercept;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JudgmentOfAuthority {


    public Boolean findPermission(String token, HttpServletRequest request, HttpServletResponse response) throws Exception {
            return true;
    }
    private void handleFalseResponse(HttpServletResponse response) throws Exception {
        response.setStatus(403);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"insufficientPermissions\":\"false\"}");
        response.getWriter().flush();
    }
}
