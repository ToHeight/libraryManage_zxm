package sale.ljw.backend.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@ApiModel(value = "添加书籍")
public class AddBook {
    @ApiModelProperty(value = "图书种类id")
    @NotNull(message = "图书种类不得为空")
    private String typeId;
    @ApiModelProperty(value = "图书名称")
    @NotNull(message = "图书名称不得为空")
    private String bookName;
    @ApiModelProperty(value = "图书作者")
    @NotBlank(message = "图书作者不得为空")
    private String bookAuthor;
    @ApiModelProperty(value = "图书信息")
    @NotBlank(message = "图书信息不得为空")
    private String bookInfo;
    @ApiModelProperty(value = "图书封面")
    @NotBlank(message = "图书封面不得为空")
    private String bookImage;
    @ApiModelProperty(value = "图书地点")
    @NotBlank(message = "图书地点不得为空")
    private String bookAddress;
    @ApiModelProperty(value = "图书状态")
    @Pattern(regexp = "^BS[0-9]{3}$", message = "图书状态编码格式错误")
    @NotBlank(message = "图书状态不得为空")
    private String bookStatus;
    @ApiModelProperty(value = "图书数量")
    @NotNull(message = "图书数量不得为空")
    private Integer bookCount;
    @ApiModelProperty(value = "图书语言")
    @Pattern(regexp = "^L[A-Z,0-9]{4}$", message = "图书语言编码格式错误")
    @NotBlank(message = "图书语言不得为空")
    private String bookLanguageCoding;
    @ApiModelProperty(value = "作者国家")
    @Pattern(regexp = "^C[A-Z,0-9]{4}$", message = "作者国家编码格式错误")
    @NotBlank(message = "作者国家不得为空")
    private String authorCountry;
    @ApiModelProperty(value = "图书星级")
    @NotNull(message = "图书星级不得为空")
    private Double bookStar;
}
