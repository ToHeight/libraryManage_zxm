package sale.ljw.backend.dao;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.backend.form.RoleInfoData;
import sale.ljw.backend.pojo.Roleinfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.ArrayList;
import java.util.Map;

/**
* @author 86155
* @description 针对表【roleInfo】的数据库操作Mapper
* @createDate 2022-11-11 15:32:10
* @Entity sale.ljw.backend.pojo.Roleinfo
*/
@Mapper
@Repository
public interface RoleinfoMapper extends BaseMapper<Roleinfo> {

    ArrayList<RoleInfoData> findAllRoleInfo();

    Integer addModule(Integer roleId,ArrayList<Integer> moduleId);
    @MapKey(value = "coding")
    ArrayList<Map<String, Object>> getRoleInfoList();
}




