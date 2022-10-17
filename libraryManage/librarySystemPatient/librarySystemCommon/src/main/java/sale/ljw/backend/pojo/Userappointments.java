package sale.ljw.backend.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName UserAppointments
 */
@TableName(value = "UserAppointments")
@Data
public class Userappointments implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 预约id
     */
    @TableId
    private Integer appointmentId;
    /**
     * 楼层id
     */
    private Integer floorId;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 预约时间编号
     */
    private String appointmentsId;
    /**
     * 当前预约状态
     */
    private String appointmentsStatus;
    /**
     * 创建预约时间
     */
    private Date appointmentsCreatetime;
    /**
     * 座位名称
     */
    private String seatname;
}