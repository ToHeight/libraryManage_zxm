package sale.ljw.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
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
    @TableId(value = "manager_id",type = IdType.AUTO)
    private Integer managerId;
    /**
     * 管理员登陆账号
     */
    @TableField(value = "manager_login")
    private String managerLogin;
    /**
     * 管理员密码
     */
    @TableField(value = "manager_passwd")
    private String managerPasswd;
    /**
     * 权限
     */
    @TableField(value = "permission")
    private Integer permission;
}