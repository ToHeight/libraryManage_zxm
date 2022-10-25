package sale.ljw.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName application
 */
@TableName(value = "application")
@Data
public class Application implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 报名id
     */
    @TableId(value = "applicationId")
    private long applicationid;
    /**
     * 用户Id
     */
    @TableField(value = "userId")
    private Integer userid;
    /**
     * 报名状态
     */
    @TableField(value = "applicationStatus")
    private String applicationstatus;
    /**
     * 活动id
     */
    @TableField(value = "activityId")
    private Integer activityid;
}