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
    @TableId
    private String scheduledId;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 图书名称
     */
    private String bookName;
    /**
     * 预定状态
     */
    private String scheduledStatus;
}