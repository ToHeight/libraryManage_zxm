package sale.ljw.backend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.backend.pojo.Open;

/**
 * @author 86155
 * @description 针对表【Open】的数据库操作Mapper
 * @createDate 2022-10-17 16:32:43
 * @Entity sale.ljw.backend.pojo.Open
 */
@Mapper
@Repository
public interface OpenMapper extends BaseMapper<Open> {

    Integer reservePeoperCount(Integer floorId,Integer number);

    Integer updateFloorPeopleCount(int number);
}




