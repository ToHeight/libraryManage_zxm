package sale.ljw.backend.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName UserLogin
 */
@TableName(value = "UserLogin")
@Data
public class Userlogin implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 用户id
     */
    @TableId
    private Integer userId;
    /**
     * 登录账号
     */
    private String userLogin;
    /**
     * 登陆密码
     */
    private String userPasswd;
    /**
     * 权限
     */
    private Integer permission;

}