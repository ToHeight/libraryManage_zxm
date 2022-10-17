package sale.ljw.backend.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName User
 */
@TableName(value = "User")
@Data
public class User implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 用户id
     */
    @TableId
    private Integer userId;
    /**
     * 用户姓名
     */
    private String userName;
    /**
     * 用户身份证号
     */
    private String userIdcard;
    /**
     * 用户性别
     */
    private String userGender;
    /**
     * 用户电话
     */
    private String userTelephone;
    /**
     * 用户住址
     */
    private String userAddress;
    /**
     * 用户邮箱
     */
    private String userEmail;
    /**
     * 用户头像
     */
    private String userImage;
    /**
     * 用户年龄
     */
    private Integer userAge;
    /**
     * 用户生日
     */
    private Date userBirth;
    /**
     * 续租次数
     */
    private Integer leaserenewalnumber;
    /**
     * 逻辑删除
     */
    private Integer userDelete;
}