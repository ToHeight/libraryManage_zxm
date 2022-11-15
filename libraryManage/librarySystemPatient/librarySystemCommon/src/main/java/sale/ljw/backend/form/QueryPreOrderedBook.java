package sale.ljw.backend.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@ApiModel(value = "查询全部预定图书")
public class QueryPreOrderedBook {
    @ApiModelProperty(value = "预定状态")
    @Pattern(regexp = "^RS[0-9]{3}$", message = "预定状态编码格式错误")
    private String reserveStatus;
    @ApiModelProperty(value = "页码")
    @NotNull(message = "页码不得为空")
    private Integer page;
    @ApiModelProperty(value = "图书名称")
    private String bookName;
}
