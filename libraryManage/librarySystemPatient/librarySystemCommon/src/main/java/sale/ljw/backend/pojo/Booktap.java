package sale.ljw.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName bookTap
 */
@TableName(value = "bookTap")
@Data
public class Booktap implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 图书id
     */
    @TableField(value = "bookId")
    private String bookid;
    /**
     * 标签id
     */
    @TableField(value = "tapId")
    private Integer tapid;
    /**
     * 用户id
     */
    @TableField(value = "userId")
    private Integer userid;
}