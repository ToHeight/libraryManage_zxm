package sale.ljw.backend.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName Book
 */
@TableName(value = "Book")
@Data
public class Book implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 图书id
     */
    @TableId
    private String bookId;
    /**
     * 图书种类id
     */
    private Integer typeId;
    /**
     * 图书名称
     */
    private String bookName;
    /**
     * 图书作者
     */
    private String bookAuthor;
    /**
     * 图书信息
     */
    private String bookInfo;
    /**
     * 图书图片
     */
    private String bookImage;
    /**
     * 图书位置
     */
    private String bookAddress;
    /**
     * 图书状态
     */
    private String bookStatus;
    /**
     * 图书数量
     */
    private Integer bookCount;
    /**
     * 逻辑删除
     */
    private Integer bookDelete;
    /**
     * 借阅次数
     */
    private Integer borrowNumber;
    /**
     * 图书语言
     */
    private String bookLanguage;
    /**
     * 作者国家
     */
    private String authorCountry;
    /**
     * 图书星级
     */
    private Double bookStar;
}