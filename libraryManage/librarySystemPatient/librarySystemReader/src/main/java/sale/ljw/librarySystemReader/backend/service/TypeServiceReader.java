package sale.ljw.librarySystemReader.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import sale.ljw.backend.pojo.Type;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author 86155
 * @description 针对表【Type】的数据库操作Service
 * @createDate 2022-10-17 16:36:05
 */
public interface TypeServiceReader extends IService<Type> {

    ArrayList<Map<String, Object>> getBookType();

}
