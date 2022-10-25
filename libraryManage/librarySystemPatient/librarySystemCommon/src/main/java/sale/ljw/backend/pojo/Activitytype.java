package sale.ljw.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName activityType
 */
@TableName(value = "activityType")
@Data
public class Activitytype implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 活动种类Id
     */
    @TableId(type = IdType.AUTO,value = "activityTypeId")
    private Integer activitytypeid;
    /**
     * 种类名称
     */
    @TableField(value = "activityTypeName")
    private String activitytypename;
    /**
     * 种类介绍
     */
    @TableField(value = "activityTypeInfo")
    private String activitytypeinfo;
}