package sale.ljw.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName Open
 */
@TableName(value = "Open")
@Data
public class Open implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 楼层id
     */
    @TableId(value = "floor_id",type = IdType.AUTO)
    private Integer floorId;
    /**
     * 楼层名称
     */
    @TableField(value = "floor_name")
    private String floorName;
    /**
     * 楼层状态
     */
    @TableField(value = "floor_status")
    private String floorStatus;
    /**
     * 预约人数
     */
    @TableField(value = "people_count")
    private Integer peopleCount;
    /**
     * 时间id
     */
    @TableField(value = "time_id")
    private Integer timeId;
}