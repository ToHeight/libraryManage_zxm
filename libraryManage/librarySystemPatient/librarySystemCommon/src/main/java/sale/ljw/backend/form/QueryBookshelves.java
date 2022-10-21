package sale.ljw.backend.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;

/**
 * 插叙书架列表
 */
@ApiModel(value = "查询书架")
@Data
public class QueryBookshelves {
    @ApiModelProperty(value = "页码")
    @NotNull(message = "页码不得为空")
    private Integer page;

    @ApiModelProperty(value = "当前页条数")
    @NotNull(message = "每页条数不得为空")
    private Integer pageSize;

    @ApiModelProperty(value = "图书名称")
    private String bookName;

    @ApiModelProperty(value = "作者姓名")
    private String authorName;

    @ApiModelProperty(value = "图书种类")
    @Pattern(regexp = "^[0-9]*$", message = "图书类别编码格式错误")
    private String bookType;

    @ApiModelProperty(value = "用户id")
    @Null
    private String userId;
}
