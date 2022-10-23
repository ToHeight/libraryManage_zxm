package sale.ljw.backend.pojo;

import com.baomidou.mybatisplus.annotation.*;
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
    @TableId(value = "borrow_id",type = IdType.AUTO)
    private Integer borrowId;
    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Integer userId;
    /**
     * 图书id
     */
    @TableField(value = "book_id")
    private String bookId;
    /**
     * 借阅时间
     */
    @TableField(value = "borrow_time")
    private Date borrowTime;
    /**
     * 归还时间
     */
    @TableField(value = "return_time")
    private Date returnTime;
    /**
     * 借阅状态
     */
    @TableField(value = "borrow_tatus")
    private String borrowTatus;
    /**
     * 逻辑删除
     */
    @TableLogic(value = "borrow_delete")
    private Integer borrowDelete;
}