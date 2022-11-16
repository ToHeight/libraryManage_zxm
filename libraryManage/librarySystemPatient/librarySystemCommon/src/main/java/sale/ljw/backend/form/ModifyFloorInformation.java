package sale.ljw.backend.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "修改楼层信息")
public class ModifyFloorInformation {
    @ApiModelProperty(value = "楼层id")
    @NotNull(message = "楼层id不得为空")
    private Integer floorId;
    @ApiModelProperty(value = "楼层状态")
    @NotBlank(message = "楼层状态不得为空")
    private String floorStatus;
    @ApiModelProperty(value = "时间id")
    @NotNull(message = "时间id不得为空")
    private Integer timeId;
}
