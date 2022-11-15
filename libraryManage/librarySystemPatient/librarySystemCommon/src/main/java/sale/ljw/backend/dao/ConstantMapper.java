package sale.ljw.backend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.backend.pojo.Constant;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author 86155
 * @description 针对表【Constant】的数据库操作Mapper
 * @createDate 2022-10-17 16:27:38
 * @Entity sale.ljw.backend.pojo.Constant
 */
@Mapper
@Repository
public interface ConstantMapper extends BaseMapper<Constant> {
    @MapKey(value = "coding")
    ArrayList<Map<String, Object>> getAuthorCountry();
    @MapKey(value = "coding")
    ArrayList<Map<String, Object>> getBookLanguage();
    @MapKey(value = "coding")
    ArrayList<Map<String, Object>> getBorrowStatus();
    @MapKey(value = "coding")
    ArrayList<Map<String, Object>> getPreOrderBookStatus();
    @MapKey(value = "coding")
    ArrayList<Map<String, Object>> getActivityStatus();
    @MapKey(value = "coding")
    ArrayList<Map<String, Object>> getFloor();
    @MapKey(value = "coding")
    ArrayList<Map<String, Object>> getGender();
    @MapKey(value = "coding")
    ArrayList<Map<String, Object>> getBookStatus();
    @MapKey(value = "coding")
    ArrayList<Map<String, Object>> findAllConstant(String value);
}




