package sale.ljw.backend.dao;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import sale.ljw.backend.pojo.Rolemodule;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.ArrayList;
import java.util.Map;

/**
* @author 86155
* @description 针对表【roleModule】的数据库操作Mapper
* @createDate 2022-11-11 15:32:14
* @Entity sale.ljw.backend.pojo.Rolemodule
*/
@Mapper
@Repository
public interface RolemoduleMapper extends BaseMapper<Rolemodule> {
    @MapKey(value = "coding")
    ArrayList<Map<String,Object>> findAllModule();
    @MapKey(value = "moduleId")
    ArrayList<Map<String,Object>> queryPermission(Integer permissionId);
}




