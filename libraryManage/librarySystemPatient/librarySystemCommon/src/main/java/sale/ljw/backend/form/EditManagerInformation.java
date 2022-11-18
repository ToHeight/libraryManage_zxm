package sale.ljw.backend.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@ApiModel(value = "修改管理员基本信息")
public class EditManagerInformation {
    @ApiModelProperty(value = "管理员id")
    @NotNull(message = "管理员id不得为空")
    private Integer managerId;
    @ApiModelProperty(value = "名称")
    @NotBlank(message = "名称不得为空")
    private String managerName;
    @ApiModelProperty(value = "性别")
    @NotBlank(message = "性别不得为空")
    @Pattern(regexp = "^GM[0-9]{3}$", message = "性别类别编码格式错误")
    private String gender;
    @ApiModelProperty(value = "电话")
    @NotBlank(message = "电话不得为空")
    @Pattern(regexp = "^1[3456789]\\d{9}$", message = "用户电话号码格式错误")
    private String telephone;
    @ApiModelProperty(value = "权限id")
    @NotNull(message = "权限id不得为空")
    private Integer permissionId;
}
