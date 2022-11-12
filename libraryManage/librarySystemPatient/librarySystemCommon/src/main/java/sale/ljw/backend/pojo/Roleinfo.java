package sale.ljw.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName roleInfo
 */
@TableName(value ="roleInfo")
@Data
public class Roleinfo implements Serializable {
    /**
     * 权限id
     */
    @TableId(type = IdType.AUTO)
    private Integer roleId;

    /**
     * 权限名称
     */
    private String name;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}