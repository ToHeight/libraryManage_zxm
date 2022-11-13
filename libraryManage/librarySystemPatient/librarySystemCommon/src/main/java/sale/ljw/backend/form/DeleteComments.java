package sale.ljw.backend.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "删除留言")
public class DeleteComments {
    @ApiModelProperty(value = "留言id")
    @NotBlank(message = "留言id不得为空")
    private String commentsId;
    @ApiModelProperty(value = "删除原因")
    @NotBlank(message = "删除原因不得为空")
    private String info;
}
