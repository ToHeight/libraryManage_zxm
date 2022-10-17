package sale.ljw.backend.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName Constant
 */
@TableName(value = "Constant")
@Data
public class Constant implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 编码
     */
    private String coding;
    /**
     * 值
     */
    private String value;
}