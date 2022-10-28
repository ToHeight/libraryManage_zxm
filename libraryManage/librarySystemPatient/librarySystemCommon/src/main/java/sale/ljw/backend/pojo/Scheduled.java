package sale.ljw.backend.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName Scheduled
 */
@TableName(value = "Scheduled")
@Data
public class Scheduled implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 预定id
     */
    @TableId(value = "scheduled_id")
    private Long scheduledId;
    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Integer userId;
    /**
     * 图书名称
     */
    @TableField(value = "book_name")
    private String bookName;
    /**
     * 预定状态
     */
    @TableField(value = "scheduled_status")
    private String scheduledStatus;
}