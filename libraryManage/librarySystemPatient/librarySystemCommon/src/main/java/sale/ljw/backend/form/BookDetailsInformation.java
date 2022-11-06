package sale.ljw.backend.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Map;

@Data
@ApiModel(value = "图书详情界面展示信息")
public class BookDetailsInformation {
    @ApiModelProperty(value = "图书id")
    private String bookId;
    @ApiModelProperty(value = "图书名称")
    private String bookName;
    @ApiModelProperty(value = "作者姓名")
    private String author;
    @ApiModelProperty(value = "图书地址")
    private String address;
    @ApiModelProperty(value = "图书种类")
    private String typeName;
    @ApiModelProperty(value = "作者国家")
    private String country;
    @ApiModelProperty(value = "图书语言")
    private String bookLanguage;
    @ApiModelProperty(value = "图书借阅次数")
    private String bookCount;
    @ApiModelProperty(value = "图书状态")
    private String bookStatus;
    @ApiModelProperty(value = "图书星级")
    private Double bookStar;
    @ApiModelProperty(value = "图书详情")
    private String bookInfo;
    @ApiModelProperty(value = "图书图片")
    private String bookImage;
    @ApiModelProperty(value = "图书标签")
    private ArrayList<Map<String,Object>> tagList;

}
