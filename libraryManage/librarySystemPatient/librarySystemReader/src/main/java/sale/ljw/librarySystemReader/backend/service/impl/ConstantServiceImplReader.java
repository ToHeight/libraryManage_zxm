package sale.ljw.librarySystemReader.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sale.ljw.backend.dao.ConstantMapper;
import sale.ljw.backend.pojo.Constant;
import sale.ljw.librarySystemReader.backend.service.ConstantServiceReader;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author 86155
 * @description 针对表【Constant】的数据库操作Service实现
 * @createDate 2022-10-17 16:27:38
 */
@Service
public class ConstantServiceImplReader extends ServiceImpl<ConstantMapper, Constant>
        implements ConstantServiceReader {
    @Autowired
    private ConstantMapper constantMapper;

    @Override
    public ArrayList<Map<String, Object>> getAuthorCountry() {
        ArrayList<Map<String, Object>> authorCountry=constantMapper.getAuthorCountry();
        return authorCountry;
    }

    @Override
    public ArrayList<Map<String, Object>> getBookLanguage() {
        ArrayList<Map<String, Object>> bookLanguage=constantMapper.getBookLanguage();
        return bookLanguage;
    }
}




