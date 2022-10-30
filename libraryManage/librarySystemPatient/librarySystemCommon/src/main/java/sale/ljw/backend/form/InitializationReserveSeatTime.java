package sale.ljw.backend.form;

import cn.hutool.core.date.DateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
@ApiModel(value = "初始化座位")
public class InitializationReserveSeatTime {
    @ApiModelProperty(value = "预约时间")
    @NotNull(message = "预约时间不得为空")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private DateTime appointmentTime;

    @ApiModelProperty(value = "预约楼层")
    @NotNull(message = "预约楼层不得为空")
    private Integer floorId;

}
