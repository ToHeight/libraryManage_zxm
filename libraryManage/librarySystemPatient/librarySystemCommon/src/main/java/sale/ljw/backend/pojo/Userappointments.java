package sale.ljw.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.sql.Time;
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
    @TableId(value = "appointment_id",type = IdType.AUTO)
    private Integer appointmentId;
    /**
     * 楼层id
     */
    @TableField(value = "floor_id")
    private Integer floorId;
    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Integer userId;
    /**
     * 预约时间编号
     */
    @TableField(value = "appointment_time")
    private Date appointmentTime;
    /**
     * 当前预约状态
     */
    @TableField(value = "appointments_status")
    private String appointmentsStatus;
    /**
     * 创建预约时间
     */
    @TableField(value = "appointments_createTime")
    private Date appointmentsCreatetime;
    /**
     * 座位名称
     */
    @TableField(value = "seatName")
    private String seatname;
}