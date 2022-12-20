package sale.ljw.librarySystemAdmin.backend.api;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sale.ljw.backend.form.EditConstant;
import sale.ljw.backend.form.QueryMessages;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.librarySystemAdmin.backend.service.ConstantServiceAdmin;

import java.util.Map;

@RestController
@RequestMapping("/constantByAdmin")
public class AConstantServlet {
    @Autowired
    private ConstantServiceAdmin constantServiceAdmin;

    /**
     * 查询全部编码
     * @param page
     * @return
     */
    @ApiOperation(value = "查询全部编码")
    @PreAuthorize("hasAnyAuthority('4') or hasRole('1')")
    @PostMapping("/findAllConstant")
    public ResponseResult<PageInfo<Map<String,Object>>> findAllConstant(@RequestBody QueryMessages page){
      return   constantServiceAdmin.findAllConstant(page);
    }

    /**
     * 修改编码数值
     * @return
     */
    @ApiOperation(value = "修改编码数值")
    @PreAuthorize("hasAnyAuthority('4') or hasRole('1')")
    @PostMapping("/editConstant")
    public ResponseResult<String> editConstant(@RequestBody EditConstant editConstant){
        return constantServiceAdmin.editConstant(editConstant);
    }
}
