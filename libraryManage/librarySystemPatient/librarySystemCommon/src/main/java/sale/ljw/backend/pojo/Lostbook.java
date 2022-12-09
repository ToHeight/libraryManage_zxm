package sale.ljw.backend.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName lostBook
 */
@TableName(value = "lostBook")
@Data
public class Lostbook implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    @TableId(value = "id")
    private String id;
    /**
     * 借阅id
     */
    @TableField(value = "borrowId")
    private Integer borrowid;
    /**
     *
     */
    @TableField(value = "lostStatus")
    private String loststatus;
    /**
     *
     */
    @TableField(value = "payId")
    private String payid;
    /**
     *
     */
    @TableField(value = "payDate")
    private Date paydate;
}