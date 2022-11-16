package sale.ljw.librarySystemAdmin.backend.api;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sale.ljw.backend.form.ModifyFloorInformation;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.librarySystemAdmin.backend.service.OpenServiceAdmin;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/floorByAdmin")
public class AFloorServlet {
    @Autowired
    private OpenServiceAdmin openServiceAdmin;

    /**
     * 查询全部楼层
     * @param page
     * @return
     */
    @ApiOperation(value = "查询全部楼层")
    @GetMapping("/findAllFloor/{page}")
    public ResponseResult<PageInfo<Map<String,Object>>> findAllFloor(@PathVariable Integer page){
        return openServiceAdmin.findAllFloor(page);
    }

    /**
     * 修改楼层信息
     * @param modifyFloorInformation
     * @return
     */
    @ApiOperation(value = "修改楼层信息")
    @PostMapping("/modifyFloorInformation")
    public ResponseResult<String> modifyFloorInformation(@RequestBody @Valid ModifyFloorInformation modifyFloorInformation){
        return openServiceAdmin.modifyFloorInformation(modifyFloorInformation);
    }
}
