package sale.ljw.backend.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@ApiModel(value = "预约座位")
public class ReserveSeatForm {
    @ApiModelProperty(value = "预约时间")
    @NotNull(message = "预约时间不得为空")
    private Date reserveTime;

    @ApiModelProperty(value = "楼层号")
    @NotNull(message = "楼层不得为空")
    private Integer floorId;

    @ApiModelProperty(value = "座位编号")
    @NotBlank(message = "座位编号不得为空")
    private String seatNumber;
}
