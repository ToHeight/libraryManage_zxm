package sale.ljw.librarySystemAdmin.backend.api;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sale.ljw.backend.form.QueryMessages;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.librarySystemAdmin.backend.service.UserappointmentsServiceAdmin;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/userAppointmentsByAdmin")
public class AUserAppointmentsServlet {
    @Autowired
    private UserappointmentsServiceAdmin userappointmentsServiceAdmin;

    /**
     * 查询全部预约信息
     * @param queryMessages
     * @return
     */
    @ApiOperation(value = "查询全部预约信息")
    @PostMapping("/findAllAppointment")
    public ResponseResult<PageInfo<Map<String,Object>>> findAllAppointment(@RequestBody @Valid QueryMessages queryMessages){
       return userappointmentsServiceAdmin.findAllAppointment(queryMessages);
    }

    /**
     * 取消预约
     * @param appintmentId
     * @return
     */
    @ApiOperation(value = "取消预约")
    @GetMapping("/cancelAppointment/{appintmentId}")
    public ResponseResult<String> cancelAppointment(@PathVariable Integer appintmentId){
        return userappointmentsServiceAdmin.cancelAppointment(appintmentId);
    }
}
