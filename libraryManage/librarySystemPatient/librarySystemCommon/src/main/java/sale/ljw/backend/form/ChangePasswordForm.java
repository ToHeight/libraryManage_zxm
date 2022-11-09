package sale.ljw.backend.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "修改密码")
public class ChangePasswordForm {
    @ApiModelProperty(value = "登陆账号")
    @NotBlank(message = "登陆账号不得为空")
    private String loginName;

    @ApiModelProperty(value = "登陆密码")
    @NotBlank(message = "登陆密码不得为空")
    private String password;

    @ApiModelProperty(value = "密钥")
    @NotBlank(message = "密钥不得为空")
    private String code;
}
