package sale.ljw.backend.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName AppointmentsTime
 */
@TableName(value = "AppointmentsTime")
@Data
public class Appointmentstime implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 时间id
     */
    @TableId
    private Integer timeId;
    /**
     * 开始时间
     */
    private Date timeStart;
    /**
     * 结束时间
     */
    private Date timeEnd;
}