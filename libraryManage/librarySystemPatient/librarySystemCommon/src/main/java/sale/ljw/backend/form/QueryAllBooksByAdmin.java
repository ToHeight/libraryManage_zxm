package sale.ljw.backend.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@ApiModel(value = "查询图书")
public class QueryAllBooksByAdmin {
    @ApiModelProperty(value = "页码")
    @NotNull(message = "页码不得为空")
    private Integer page;
    @ApiModelProperty(value = "查询信息")
    private String info;
    @ApiModelProperty(value = "图书种类")
    private Integer typeId;
    @ApiModelProperty(value = "图书状态")
    @Pattern(regexp = "^BS[0-9]{3}$", message = "图书状态编码格式错误")
    private String bookStatus;
    @ApiModelProperty(value = "图书语言")
    @Pattern(regexp = "^L[A-Z,0-9]{4}$", message = "图书语言编码格式错误")
    private String bookLanguageCoding;
    @ApiModelProperty(value = "作者国家")
    @Pattern(regexp = "^C[A-Z,0-9]{4}$", message = "作者国家编码格式错误")
    private String authorCountry;
}
