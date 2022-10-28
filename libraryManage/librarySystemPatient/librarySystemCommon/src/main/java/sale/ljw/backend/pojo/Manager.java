package sale.ljw.backend.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName Manager
 */
@TableName(value = "Manager")
@Data
public class Manager implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 管理员id
     */
    @TableId(value = "manager_id",type = IdType.AUTO)
    private Integer managerId;
    /**
     * 管理员姓名
     */
    @TableField(value = "manager_name")
    private String managerName;
    /**
     * 管理员年龄
     */
    @TableField(value = "manager_age")
    private Integer managerAge;
    /**
     * 管理员性别
     */
    @TableField(value = "manager_gender")
    private String managerGender;
    /**
     * 管理员电话
     */
    @TableField(value = "manager_telephone")
    private String managerTelephone;
    /**
     * 管理员地址
     */
    @TableField(value = "manager_address")
    private String managerAddress;
    /**
     * 逻辑删除
     */
    @TableLogic(value = "manager_delete")
    private Integer managerDelete;
}