package sale.ljw.backend.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@ApiModel(value = "根据条件查找图书")
public class FindBookByCriteria implements Serializable {

    @ApiModelProperty(value = "页码")
    @NotNull(message = "页码不得为空")
    private Integer page;

    @ApiModelProperty(value = "当前页条数")
    @NotNull(message = "每页条数不得为空")
    private Integer pageSize;

    @ApiModelProperty(value = "图书名称")
    private String bookName;

    @ApiModelProperty(value = "图书作者")
    private String bookAuthor;

    @ApiModelProperty(value = "图书类别")
    @Pattern(regexp = "^[0-9]*$", message = "图书类别编码格式错误")
    private String bookTypeId;

    @ApiModelProperty(value = "图书语言")
    @Pattern(regexp = "^L[A-Z,0-9]{4}$", message = "图书语言编码格式错误")
    private String bookLanguageCoding;

    @ApiModelProperty(value = "作者国家")
    @Pattern(regexp = "^C[A-Z,0-9]{4}$", message = "作者国家编码格式错误")
    private String authorCountry;

    @ApiModelProperty(value = "图书星级")
    private Double bookStarMin;

    @ApiModelProperty(value = "根据借阅次数升序或降序")
    private Boolean borrowNumber;

}
