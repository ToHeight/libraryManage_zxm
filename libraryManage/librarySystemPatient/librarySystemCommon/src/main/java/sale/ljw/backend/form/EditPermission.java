package sale.ljw.backend.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@Data
@ApiModel(value = "修改权限")
public class EditPermission {
    @ApiModelProperty(value = "权限id")
    @NotNull(message = "权限id不得为空")
    private Integer roleId;
    @ApiModelProperty(value="权限名称")
    @NotBlank(message = "权限名称不得为空")
    private String roleName;
    @ApiModelProperty(value = "模块id")
    private ArrayList<Integer> moduleId;
}
