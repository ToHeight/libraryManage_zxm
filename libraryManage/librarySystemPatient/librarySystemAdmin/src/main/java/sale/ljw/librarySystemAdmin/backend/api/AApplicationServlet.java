package sale.ljw.librarySystemAdmin.backend.api;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sale.ljw.backend.form.QueryMessages;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.librarySystemAdmin.backend.service.ApplicationServiceAdmin;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/applicationByAdmin")
public class AApplicationServlet {
    @Autowired
    private ApplicationServiceAdmin applicationServiceAdmin;

    /**
     * 查询全部报名人员
     *
     * @param queryMessages
     * @return
     */
    @ApiOperation(value = "查询全部报名人员")
    @PreAuthorize("hasAnyAuthority('5') or hasAnyRole('1','5')")
    @PostMapping("/findAllApplication")
    public ResponseResult<PageInfo<Map<String, Object>>> findAllApplication(@RequestBody @Valid QueryMessages queryMessages) {
        return applicationServiceAdmin.findAllApplication(queryMessages);
    }

    /**
     * 报名成功
     * @param applicationId
     * @return
     */
    @ApiOperation(value = "报名成功")
    @PreAuthorize("hasAnyAuthority('5') or hasAnyRole('1','5')")
    @GetMapping("/signUpSuccessfully/{applicationId}")
    public ResponseResult<String> signUpSuccessfully(@PathVariable String applicationId){
        return applicationServiceAdmin.signUpSuccessfully(applicationId);
    }

    /**
     * 取消报名
     * @param applicationId
     * @return
     */
    @ApiOperation(value = "取消报名")
    @PreAuthorize("hasAnyAuthority('5') or hasAnyRole('1','5')")
    @GetMapping("/cancelRegistration/{applicationId}")
    public ResponseResult<String> cancelRegistration(@PathVariable Integer applicationId){
        return applicationServiceAdmin.cancelRegistration(applicationId);
    }
}
