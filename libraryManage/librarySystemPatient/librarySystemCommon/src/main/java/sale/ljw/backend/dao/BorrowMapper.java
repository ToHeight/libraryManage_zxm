package sale.ljw.backend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.backend.form.FindBorrowedBooks;
import sale.ljw.backend.pojo.Borrow;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author 86155
 * @description 针对表【Borrow】的数据库操作Mapper
 * @createDate 2022-10-17 16:24:02
 * @Entity sale.ljw.backend.pojo.Borrow
 */
@Mapper
@Repository
public interface BorrowMapper extends BaseMapper<Borrow> {
    @MapKey(value = "borrowId")
    ArrayList<Map<String, Object>> findBorrowedBooks(FindBorrowedBooks findBorrowedBooks, String userId);
}




