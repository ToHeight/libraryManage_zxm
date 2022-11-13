package sale.ljw.backend.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Map;

@ApiModel(value = "图书标签列表")
@Data
public class BookTagList {
    private Integer tagId;
    private String tagValue;
    private String userName;
    private ArrayList<Map<String,Object>> tapList;
}
