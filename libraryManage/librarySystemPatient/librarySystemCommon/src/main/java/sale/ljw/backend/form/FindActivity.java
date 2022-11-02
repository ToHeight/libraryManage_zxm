package sale.ljw.backend.form;

import com.sun.org.apache.xpath.internal.operations.Bool;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;

@ApiModel(value = "查询活动")
@Data
public class FindActivity {
    @ApiModelProperty(value = "页码")
    @NotNull(message = "页码不得为空")
    private Integer page;

    @ApiModelProperty(value = "显示页数")
    @NotNull(message = "显示页数不得为空")
    private Integer pageSize;

    @ApiModelProperty(value = "活动名称")
    private String activityName;

    @ApiModelProperty(value = "举办方名称")
    private String activityOrganizer;

    @ApiModelProperty(value = "活动种类id")
    @Pattern(regexp = "^[0-9]*$", message = "活动种类id编码格式错误")
    private String activityTypeId;

    @ApiModelProperty(value = "活动当前状态")
    @Pattern(regexp = "^ACS[0-9]{2}$", message = "活动当前状态编码格式错误")
    private String activityStatus;

    @ApiModelProperty(value = "自身相关的活动")
    @NotNull(message = "自身相关的活动需给值")
    private Boolean itself;

    @ApiModelProperty(value = "不显示活动id")
    private ArrayList<Integer> notShowActivityId;

}
