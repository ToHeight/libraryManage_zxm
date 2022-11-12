package sale.ljw.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName moduleInfo
 */
@TableName(value ="moduleInfo")
@Data
public class Moduleinfo implements Serializable {
    /**
     * 模块id
     */
    private Integer moduleId;

    /**
     * 模块名称
     */
    private String name;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}