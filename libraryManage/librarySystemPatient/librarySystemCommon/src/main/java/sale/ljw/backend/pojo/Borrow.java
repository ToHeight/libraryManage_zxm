package sale.ljw.backend.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName Borrow
 */
@TableName(value = "Borrow")
@Data
public class Borrow implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 借阅id
     */
    @TableId
    private Integer borrowId;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 图书id
     */
    private String bookId;
    /**
     * 借阅时间
     */
    private Date borrowTime;
    /**
     * 归还时间
     */
    private Date returnTime;
    /**
     * 借阅状态
     */
    private String borrowTatus;
    /**
     * 逻辑删除
     */
    private Integer borrowDelete;
}