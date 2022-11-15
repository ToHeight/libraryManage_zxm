package sale.ljw.backend.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "查询全部借阅信息")
public class QueryAllBorrow {
    @ApiModelProperty(value = "页码")
    @NotNull(message = "页码不得为空")
    private Integer page;
    @ApiModelProperty(value = "用户姓名")
    private String userName;
    @ApiModelProperty(value = "借阅状态")
    private String borrowStatus;
}
