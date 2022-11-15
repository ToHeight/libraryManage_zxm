package sale.ljw.backend.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@ApiModel(value = "添加活动")
public class AddActivity {
    private Integer activityId;
    @ApiModelProperty(value = "活动名称")
    @NotBlank(message = "活动名称不得为空")
    private String activityName;
    @ApiModelProperty(value = "活动种类di")
    @NotNull(message = "活动种类di不得为空")
    private Integer activityTypeId;
    @ApiModelProperty(value = "活动介绍")
    @NotBlank(message = "活动介绍不得为空")
    private String activityInfo;
    @ApiModelProperty(value = "活动举办方")
    @NotBlank(message = "活动举办方不得为空")
    private String activityOrganizer;
    @ApiModelProperty(value = "活动截至日期")
    @NotNull(message = "活动截至日期不得为空")
    private Date activityDate;
    @ApiModelProperty(value = "报名截至日期")
    @NotNull(message = "活动报名截至日期不得为空")
    private Date activityApplication;
    @ApiModelProperty(value = "活动最低年龄")
    @NotNull(message = "活动年龄不得为空")
    private Integer activityAge;
}
