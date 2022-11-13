package sale.ljw.backend.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@ApiModel(value = "图书留言")
public class BookMessage {
    @ApiModelProperty(value = "借阅id")
    @NotNull(message = "借阅id不得为空")
    private Integer borrowId;

    @ApiModelProperty(value = "图书id")
    @NotBlank(message = "图书id不得为空")
    private String bookId;

    @ApiModelProperty(value = "留言内容")
    @NotBlank(message = "留言内容不得为空")
    @Length(min = 10)
    private String message;

    @ApiModelProperty(value = "是否覆盖原有评论")
    @AssertTrue(message = "未选择覆盖原有评论")
    private Boolean cover;
}
