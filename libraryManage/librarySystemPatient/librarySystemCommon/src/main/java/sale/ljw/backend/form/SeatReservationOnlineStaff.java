package sale.ljw.backend.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;

@Data
@ApiModel(value = "当前在线人员")
public class SeatReservationOnlineStaff {
    @ApiModelProperty(value = "旧地址")
    private String oldSeatNumber;
    @ApiModelProperty(value = "是否是预约成功座位")
    private Boolean appointmentSuccessful;
    @ApiModelProperty(value = "新地址")
    private String newSeatNumber;

    @ApiModelProperty(value = "用户信息")
    private HashMap<String, Object> userInformation;
}
