package sale.ljw.backend.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
    @TableId
    private Integer commentsId;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 图书id
     */
    private String bookId;
    /**
     * 留言内容
     */
    private String commentsInfo;
    /**
     * 逻辑删除
     */
    private Integer deleteComments;
}