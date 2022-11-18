package sale.ljw.backend.form;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.Map;

@Data
@ApiModel(value = "获取全部数据")
public class LoginAdminInformation {
    private Integer managerId;
    private String managerName;
    private Integer managerAge;
    private Integer permissionId;
    private ArrayList<Map<String,Object>> permissionList;
}
