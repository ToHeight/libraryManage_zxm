package sale.ljw.backend.pojo;

import com.baomidou.mybatisplus.annotation.*;
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
    @TableId(type = IdType.AUTO,value = "tag_id")
    private Integer tagId;
    /**
     * 标签值
     */
    @TableField(value = "tag_value")
    private String tagValue;
    /**
     * 逻辑删除
     */
    @TableLogic(value = "tag_delete")
    private Integer tagDelete;
    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Integer userId;
}