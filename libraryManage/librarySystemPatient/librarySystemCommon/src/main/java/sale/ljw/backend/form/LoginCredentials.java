package sale.ljw.backend.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 登录所需信息
 */
@Data
@ApiModel(value = "登录凭证")
public class LoginCredentials implements Serializable {

    @ApiModelProperty(value = "登录名")
    @NotBlank(message = "登录名不得为空")
    private String userLoginName;

    @ApiModelProperty(value = "登录密码")
    @NotBlank(message = "登录密码不得为空")
    private String password;

}
