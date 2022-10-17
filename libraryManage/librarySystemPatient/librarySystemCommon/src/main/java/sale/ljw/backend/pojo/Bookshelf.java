package sale.ljw.backend.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
    @TableId
    private Integer shelfId;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 图书id
     */
    private String bookId;
    /**
     * 添加时间
     */
    private Date addTime;
    /**
     * 图书评价
     */
    private Integer bookStar;
}