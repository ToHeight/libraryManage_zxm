package sale.ljw.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName roleModule
 */
@TableName(value ="roleModule")
@Data
public class Rolemodule implements Serializable {
    /**
     * 权限id
     */
    private Integer roleId;

    /**
     * 模块id
     */
    private Integer moduleId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}