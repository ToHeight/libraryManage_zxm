package sale.ljw.backend.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName ManagerLogin
 */
@TableName(value = "ManagerLogin")
@Data
public class Managerlogin implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 管理员id
     */
    @TableId
    private Integer managerId;
    /**
     * 管理员登陆账号
     */
    private String managerLogin;
    /**
     * 管理员密码
     */
    private String managerPasswd;
    /**
     * 权限
     */
    private Integer permission;
}