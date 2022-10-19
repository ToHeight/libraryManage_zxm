package sale.ljw.backend.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
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
    @TableId(value = "book_id")
    private String bookId;
    /**
     * 图书种类id
     */
    @TableField(value = "type_id")
    private Integer typeId;
    /**
     * 图书名称
     */
    @TableField(value = "book_name")
    private String bookName;
    /**
     * 图书作者
     */
    @TableField(value = "book_author")
    private String bookAuthor;
    /**
     * 图书信息
     */
    @TableField(value = "book_info")
    private String bookInfo;
    /**
     * 图书图片
     */
    @TableField(value = "book_image")
    private String bookImage;
    /**
     * 图书位置
     */
    @TableField(value = "book_address")
    private String bookAddress;
    /**
     * 图书状态
     */
    @TableField(value = "book_status")
    private String bookStatus;
    /**
     * 图书数量
     */
    @TableField(value = "book_count")
    private Integer bookCount;
    /**
     * 逻辑删除
     */
    @TableLogic(value = "book_delete")
    private Integer bookDelete;
    /**
     * 借阅次数
     */
    @TableField(value = "borrow_number")
    private Integer borrowNumber;
    /**
     * 图书语言
     */
    @TableField(value = "book_language")
    private String bookLanguage;
    /**
     * 作者国家
     */
    @TableField(value = "author_country")
    private String authorCountry;
    /**
     * 图书星级
     */
    @TableField(value = "book_star")
    private Double bookStar;
}