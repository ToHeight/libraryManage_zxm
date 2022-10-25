package sale.ljw.librarySystemReader.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sale.ljw.backend.dao.BooktagMapper;
import sale.ljw.backend.pojo.Booktag;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.common.common.http.StatusCode;
import sale.ljw.common.utils.JwtUtils;
import sale.ljw.librarySystemReader.backend.service.BooktagServiceReader;

/**
 * @author 86155
 * @description 针对表【bookTag】的数据库操作Service实现
 * @createDate 2022-10-17 16:19:03
 */
@Service
public class BooktagServiceImplReader extends ServiceImpl<BooktagMapper, Booktag>
        implements BooktagServiceReader {

    @Autowired
    private BooktagMapper booktagMapper;
    @Override
    public ResponseResult<String> deleteBookTag(Integer tagId, String token) {
        //执行删除
        QueryWrapper<Booktag> queryWrapper=new QueryWrapper<>();
        int userId = Integer.parseInt(JwtUtils.parseJWT(token));
        queryWrapper.eq("tag_id", tagId).eq("user_id",userId);
        if(booktagMapper.delete(queryWrapper)==0){
            return ResponseResult.getErrorResult("删除书签失败", StatusCode.NOT_MODIFIED, null);
        }
        return ResponseResult.getSuccessResult(null,"删除成功！");
    }
}




