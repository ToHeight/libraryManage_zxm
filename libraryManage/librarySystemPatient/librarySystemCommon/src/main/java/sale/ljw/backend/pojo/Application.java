package sale.ljw.backend.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
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
    private String applicationid;
    /**
     * 用户Id
     */
    private Integer userid;
    /**
     * 报名状态
     */
    private String applicationstatus;
    /**
     * 参加时间
     */
    private Date participatetime;
    /**
     * 活动id
     */
    private Integer activityid;
}