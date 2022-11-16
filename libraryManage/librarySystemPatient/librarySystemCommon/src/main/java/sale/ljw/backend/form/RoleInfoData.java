package sale.ljw.backend.form;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.Map;

@Data
@ApiModel(value = "权限展示数据")
public class RoleInfoData {
    private Integer roleId;
    private String roleInfo;
    private ArrayList<Map<String,Object>> module;
}
