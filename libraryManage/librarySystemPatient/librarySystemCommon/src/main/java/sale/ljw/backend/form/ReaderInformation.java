package sale.ljw.backend.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@ApiModel(value = "读者个人信息")
public class ReaderInformation {

    @ApiModelProperty(value = "读者名称")
    @NotBlank(message = "读者名称不得为空")
    private String userName;

    @ApiModelProperty(value = "身份证号")
    @NotBlank(message = "身份证号不得为空")
    @Pattern(regexp = "(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)", message = "身份证格式错误")
    private String idCard;

    @ApiModelProperty(value = "性别")
    @NotBlank(message = "性别编码不得为空")
    @Pattern(regexp = "^GM[0-9]{3}$", message = "性别编码格式错误")
    private String gender;

    @ApiModelProperty(value = "用户电话号码")
    @NotBlank(message = "用户电话号码不得为空")
    @Pattern(regexp = "^1[3456789]\\d{9}$",message = "用户电话号码格式错误")
    private String telephone;

    @ApiModelProperty(value = "地址")
    @NotBlank(message = "地址不得为空")
    private String address;

    @ApiModelProperty(value = "电子邮件")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$",message = "电子邮件格式错误")
    private String email;

    @ApiModelProperty(value = "电子邮件码")
    @Length(min = 5,max = 5)
    private String emailCode;

    @ApiModelProperty(value = "头像地址")
    @NotBlank(message = "头像地址不得为空")
    private String image;

    @ApiModelProperty(value = "用户生日")
    @NotNull(message = "用户生日不得为空")
    private Date birthDay;

}
