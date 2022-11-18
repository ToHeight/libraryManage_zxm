package sale.ljw.backend.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@ApiModel(value = "编辑用户信息")
public class EditUserByAdmin {
    @ApiModelProperty(value = "用户id")
    @NotNull(message = "用户id不得为空")
    private Integer userId;
    @ApiModelProperty(value = "用户姓名")
    @NotBlank(message = "用户姓名不得为空")
    private String userName;
    @ApiModelProperty(value = "用户身份证号")
    @NotBlank(message = "用户身份证号不得为空")
    @Pattern(regexp = "(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)", message = "身份证格式错误")
    private String idCard;
    @ApiModelProperty(value = "性别")
    @NotBlank(message = "性别不得为空")
    @Pattern(regexp = "^GM[0-9]{3}$", message = "性别类别编码格式错误")
    private String gender;
    @ApiModelProperty(value = "电话号")
    @NotBlank(message = "电话号码不得为空")
    @Pattern(regexp = "^1[3456789]\\d{9}$", message = "用户电话号码格式错误")
    private String telephone;
    @ApiModelProperty(value = "地址")
    @NotBlank(message = "地址不得为空")
    private String address;
    @ApiModelProperty(value = "电子邮件")
    @NotBlank(message = "电子邮件不得为空")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$", message = "电子邮件格式错误")
    private String email;
}
