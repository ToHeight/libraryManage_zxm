package sale.ljw.librarySystemReader.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sale.ljw.backend.dao.BooktagMapper;
import sale.ljw.backend.dao.BooktapMapper;
import sale.ljw.backend.pojo.Booktag;
import sale.ljw.backend.pojo.Booktap;
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
    @Autowired
    private BooktapMapper booktapMapper;
    @Transactional
    @Override
    public ResponseResult<String> deleteBookTag(Integer tagId,String bookId, String token) {
        //执行删除
        QueryWrapper<Booktag> queryWrapper=new QueryWrapper<>();
        int userId = Integer.parseInt(JwtUtils.parseJWT(token));
        queryWrapper.eq("tag_id", tagId).eq("user_id",userId);
        if(booktagMapper.delete(queryWrapper)==0){
            //判断当前tag的标签是否是公共库标签
            QueryWrapper<Booktag> queryWrapper_1=new QueryWrapper<>();
            queryWrapper_1.eq("tag_id", tagId).isNull("user_id");
            if(booktagMapper.selectCount(queryWrapper_1)!=0){
                //公共库,删除标签tap数据
                QueryWrapper<Booktap> queryWrapper_tap=new QueryWrapper<>();
                queryWrapper_tap.eq("tapId", tagId).eq("userId", userId).eq("bookId", bookId);
                if(booktapMapper.delete(queryWrapper_tap)!=0){
                    return ResponseResult.getSuccessResult(null,"删除成功！");
                }
            }
            return ResponseResult.getErrorResult("删除书签失败", StatusCode.NOT_MODIFIED, null);
        }
        return ResponseResult.getSuccessResult(null,"删除成功！");
    }
}




