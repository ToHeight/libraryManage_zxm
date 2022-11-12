package sale.ljw.backend.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName Action
 */
@TableName(value = "Action")
@Data
public class Action implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 权限id
     */
    private Integer actionId;
    /**
     * 权限名称
     */
    private String actionName;
}