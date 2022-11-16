package sale.ljw.backend.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@ApiModel(value = "修改活动")
public class ModifyActivity {
    @ApiModelProperty(value = "活动id")
    @NotNull(message = "活动id不得为空")
    private Integer activityId;
    @ApiModelProperty(value = "活动名称")
    @NotBlank(message = "活动名称不得为空")
    private String activityName;
    @ApiModelProperty(value = "活动状态")
    @NotBlank(message = "活动状态不得为空")
    private String activityStatus;
    @ApiModelProperty(value = "活动截止日期")
    @NotNull(message = "活动截至日期不得为空")
    private Date activityDate;
    @ApiModelProperty(value = "报名截止日期")
    @NotNull(message = "报名截至日期不得为空")
    private Date activityApplication;
    @ApiModelProperty(value = "报名最低年龄")
    private Integer activityAge;
}
