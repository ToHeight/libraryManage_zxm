package sale.ljw.backend.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@ApiModel(value = "修改预定数据")
public class ModifyReservationStatus {
    @ApiModelProperty(value = "预定id")
    @NotBlank(message = "预定id不得为空")
    private String scheduledId;

    @ApiModelProperty(value = "预定状态")
    @NotBlank(message = "预定状态不得为空")
    @Pattern(regexp = "^RS[0-9]{3}$", message = "预定状态编码格式错误")
    private String reserveStatus;
    
}
