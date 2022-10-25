package sale.ljw.backend.form;


import io.swagger.annotations.ApiModel;
import lombok.Data;
import sale.ljw.backend.pojo.Booktap;

import java.util.ArrayList;
import java.util.Map;


@Data
@ApiModel(value = "书架图书")
public class BookshelvesBooks {
    private String bookName;
    private String bookImage;
    private String bookAuthor;
    private String bookInfo;
    private String bookAddress;
    private Double bookStar;
    private String bookStatus;
    private String bookType;
    private String bookLanguage;
    private String bookCountry;
    private Integer borrowNumber;
    private String bookId;
    private String userId;
    private ArrayList<Map<String,Object>> bookTags;
}
