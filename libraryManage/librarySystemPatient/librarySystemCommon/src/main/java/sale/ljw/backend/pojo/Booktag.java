package sale.ljw.backend.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName bookTag
 */
@TableName(value = "bookTag")
@Data
public class Booktag implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 标签id
     */
    @TableId
    private Integer tagId;
    /**
     * 标签值
     */
    private String tagValue;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 逻辑删除
     */
    private Integer tagDelete;
}