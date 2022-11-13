package sale.ljw.backend.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName Comments
 */
@TableName(value = "Comments")
@Data
public class Comments implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 留言id
     */
    @TableId(value = "comments_id")
    private String commentsId;
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
     * 留言内容
     */
    @TableField(value = "comments_info")
    private String commentsInfo;
    /**
     * 逻辑删除
     */
    @TableLogic(value = "delete_comments")
    private Integer deleteComments;

    public Comments(String commentsId, Integer userId, String bookId, String commentsInfo) {
        this.commentsId = commentsId;
        this.userId = userId;
        this.bookId = bookId;
        this.commentsInfo = commentsInfo;
    }

    public Comments() {
    }
}