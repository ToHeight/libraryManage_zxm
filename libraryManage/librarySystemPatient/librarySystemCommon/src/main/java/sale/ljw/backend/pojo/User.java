package sale.ljw.backend.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
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
    @TableId(value = "user_id")
    private Integer userId;
    /**
     * 用户姓名
     */
    @TableField(value = "user_name")
    private String userName;
    /**
     * 用户身份证号
     */
    @TableField(value = "user_idcard")
    private String userIdcard;
    /**
     * 用户性别
     */
    @TableField(value = "user_gender")
    private String userGender;
    /**
     * 用户电话
     */
    @TableField(value = "user_telephone")
    private String userTelephone;
    /**
     * 用户住址
     */
    @TableField(value = "user_address")
    private String userAddress;
    /**
     * 用户邮箱
     */
    @TableField(value = "user_email")
    private String userEmail;
    /**
     * 用户头像
     */
    @TableField(value = "user_image")
    private String userImage;
    /**
     * 用户年龄
     */
    @TableField(value = "user_age")
    private Integer userAge;
    /**
     * 用户生日
     */
    @TableField(value = "user_birth")
    private Date userBirth;
    /**
     * 续租次数
     */
    @TableField(value = "leaseRenewalNumber")
    private Integer leaserenewalnumber;
    /**
     * 逻辑删除
     */
    @TableLogic(value = "user_delete")
    private Integer userDelete;

    /**
     * 账号状态
     */
    private String status;
    /**
     * 借阅次数
     */
    @TableField(value = "borrowing_number")
    private Integer borrowingNumber;
}