package sale.ljw.librarySystemReader.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sale.ljw.backend.dao.TypeMapper;
import sale.ljw.backend.pojo.Type;
import sale.ljw.librarySystemReader.backend.service.TypeServiceReader;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author 86155
 * @description 针对表【Type】的数据库操作Service实现
 * @createDate 2022-10-17 16:36:05
 */
@Service
public class TypeServiceImplReader extends ServiceImpl<TypeMapper, Type>
        implements TypeServiceReader {
    @Autowired
    private TypeMapper typeMapper;

    @Override
    public ArrayList<Map<String, Object>> getBookType() {
        ArrayList<Map<String, Object>> bookType = typeMapper.getBookType();
        return bookType;
    }

}




