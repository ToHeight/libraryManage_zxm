package sale.ljw.backend.pojo;

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
    @TableId
    private Integer floorId;
    /**
     * 楼层名称
     */
    private String floorName;
    /**
     * 楼层状态
     */
    private String floorStatus;
    /**
     * 预约人数
     */
    private Integer peopleCount;
    /**
     * 时间id
     */
    private Integer timeId;
}