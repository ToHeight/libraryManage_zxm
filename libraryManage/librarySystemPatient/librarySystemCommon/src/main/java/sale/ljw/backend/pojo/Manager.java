package sale.ljw.backend.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
    @TableId
    private Integer managerId;
    /**
     * 管理员姓名
     */
    private String managerName;
    /**
     * 管理员年龄
     */
    private Integer managerAge;
    /**
     * 管理员性别
     */
    private String managerGender;
    /**
     * 管理员电话
     */
    private String managerTelephone;
    /**
     * 管理员地址
     */
    private String managerAddress;
    /**
     * 逻辑删除
     */
    private Integer managerDelete;
}