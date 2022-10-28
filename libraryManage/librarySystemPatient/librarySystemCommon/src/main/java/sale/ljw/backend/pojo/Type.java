package sale.ljw.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName Type
 */
@TableName(value = "Type")
@Data
public class Type implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 图书种类id
     */
    @TableId(value = "type_id",type = IdType.AUTO)
    private Integer typeId;
    /**
     * 种类名称
     */
    @TableField(value = "type_name")
    private String typeName;
    /**
     * 种类介绍
     */
    @TableField(value = "type_info")
    private String typeInfo;
}