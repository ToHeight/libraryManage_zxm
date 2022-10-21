package sale.ljw.backend.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName BookShelf
 */
@TableName(value = "BookShelf")
@Data
public class Bookshelf implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 书架id
     */
    @TableId(type = IdType.AUTO,value = "shelf_id")
    private Integer shelfId;
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
     * 添加时间
     */
    @TableField(value = "add_time")
    private Date addTime;
    /**
     * 图书评价
     */
    @TableField(value = "book_star")
    private Integer bookStar;

    @TableLogic(value = "book_delete")
    private Integer bookDelete;
}