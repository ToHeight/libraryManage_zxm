package sale.ljw.backend.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "修改常规项")
public class EditConstant {
    @ApiModelProperty(value = "编码")
    @NotBlank(message = "编码不得为空")
    private String coding;
    @ApiModelProperty(value = "值")
    @NotBlank(message = "值不得为空")
    private String value;
}
