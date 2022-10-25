package sale.ljw.backend.pojo;

import com.baomidou.mybatisplus.annotation.*;
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
    @TableId(type = IdType.AUTO,value = "activityId")
    private Integer activityid;
    /**
     * 活动名称
     */
    @TableField(value = "activityName")
    private String activityname;
    /**
     * 活动种类id
     */
    @TableField(value = "activityTypeId")
    private Integer activitytypeid;
    /**
     * 活动信息
     */
    @TableField(value = "activityInfo")
    private String activityinfo;
    /**
     * 活动举办方
     */
    @TableField(value = "activityOrganizer")
    private String activityorganizer;
    /**
     * 活动日期
     */
    @TableField(value = "activityDate")
    private Date activitydate;
    /**
     * 报名截止日期
     */
    @TableField(value = "activityApplication")
    private Date activityapplication;
    /**
     * 活动要求年龄
     */
    @TableField(value = "activityAge")
    private Integer activityage;
    /**
     * 活动状态
     */
    @TableField(value = "activityStatus")
    private String activitystatus;
    /**
     * 逻辑删除
     */
    @TableLogic(value = "activityDelete")
    private Integer activityDelete;
}