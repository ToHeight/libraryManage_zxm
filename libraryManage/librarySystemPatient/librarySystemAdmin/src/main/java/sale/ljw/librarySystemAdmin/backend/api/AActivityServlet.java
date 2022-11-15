package sale.ljw.librarySystemAdmin.backend.api;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sale.ljw.backend.form.AddActivity;
import sale.ljw.backend.form.QueryAllActivity;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.librarySystemAdmin.backend.service.ActivityServiceAdmin;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/activityByAdmin")
public class AActivityServlet {
    @Autowired
    private ActivityServiceAdmin activityServiceAdmin;

    /**
     * 查询全部活动
     * @param queryAllActivity
     * @return
     */
    @ApiOperation(value = "获取全部活动")
    @PostMapping("/findAllActivity")
    public ResponseResult<PageInfo<Map<String,Object>>> findAllActivity(@RequestBody @Valid QueryAllActivity queryAllActivity){
        return activityServiceAdmin.findAllActivity(queryAllActivity);
    }

    /**
     * 发布活动
     * @param addActivity
     * @return
     */
    @ApiOperation(value = "发布活动")
    @PostMapping("/addActivity")
    public ResponseResult<String> addActivity(@RequestBody AddActivity addActivity){
        return activityServiceAdmin.addActivity(addActivity);
    }

    /**
     * 删除活动
     * @param activityId
     * @return
     */
    @ApiOperation(value = "删除活动")
    @GetMapping("/deleteActivity/{activityId}")
    public ResponseResult<String> deleteActivity(@PathVariable Integer activityId){
        return activityServiceAdmin.deleteActivity(activityId);
    }


}
