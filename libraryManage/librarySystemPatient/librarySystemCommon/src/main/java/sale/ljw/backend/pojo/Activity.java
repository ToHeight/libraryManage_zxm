package sale.ljw.backend.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName activity
 */
@TableName(value = "activity")
@Data
public class Activity implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 活动id
     */
    @TableId
    private Integer activityid;
    /**
     * 活动名称
     */
    private String activityname;
    /**
     * 活动种类id
     */
    private Integer activitytypeid;
    /**
     * 活动信息
     */
    private String activityinfo;
    /**
     * 活动举办方
     */
    private String activityorganizer;
    /**
     * 活动嘉宾
     */
    private String activityguests;
    /**
     * 活动地点Id
     */
    private String activityaddressid;
    /**
     * 活动日期
     */
    private Date activitydate;
    /**
     * 报名截止日期
     */
    private Date activityapplication;
    /**
     * 活动要求年龄
     */
    private Integer activityage;
    /**
     * 活动状态
     */
    private String activitystatus;
}