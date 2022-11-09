package sale.ljw.backend.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "邮箱登录密钥")
public class EmailLoginCredentials {
    @ApiModelProperty(value = "电子邮件")
    @NotBlank(message = "电子邮件不得为空")
    private String email;
    @ApiModelProperty(value = "验证码")
    @NotBlank(message = "验证码")
    private String code;
}
