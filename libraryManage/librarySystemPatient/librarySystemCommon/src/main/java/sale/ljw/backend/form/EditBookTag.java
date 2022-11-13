package sale.ljw.backend.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@Data
@ApiModel(value = "修改图书标签")
public class EditBookTag {
    @ApiModelProperty(value = "书签id")
    @NotNull(message = "书签id不得为空")
    private Integer tagId;

    @ApiModelProperty(value = "书签名称")
    @NotNull(message = "书签名称不得为空")
    private String tagValue;

    @ApiModelProperty(value = "图书id")
    private ArrayList<Integer> bookId;
}
