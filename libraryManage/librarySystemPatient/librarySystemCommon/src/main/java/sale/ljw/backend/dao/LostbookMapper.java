package sale.ljw.backend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.backend.pojo.Lostbook;

import java.util.ArrayList;
import java.util.Map;

/**
* @author 86155
* @description 针对表【lostBook】的数据库操作Mapper
* @createDate 2022-12-05 18:22:23
* @Entity sale.ljw.backend.pojo.Lostbook
*/
@Mapper
@Repository
public interface LostbookMapper extends BaseMapper<Lostbook> {

    Integer associationId(Integer borrowId, String userId);

    Integer addLostBook(Integer borrowId);
    @MapKey(value = "lostBookId")
    ArrayList<Map<String, Object>> findAllLostBook(String userId);

    ArrayList<Integer> selectLostBook();

    Integer addBorrowLost( ArrayList<Integer> borrowList);

    Integer findCountLostBook(Integer userId);
    @MapKey(value = "lostBookId")
    ArrayList<Map<String, Object>> findAllLostBookList();
    @MapKey(value = "lostBookId")
    ArrayList<Map<String, Object>> selectLostInformation(ArrayList<Integer> lostBookId);
}




