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
import sale.ljw.librarySystemReader.backend.service.BooktapServiceReader;

/**
 * @author 86155
 * @description 针对表【bookTap】的数据库操作Service实现
 * @createDate 2022-10-17 16:22:46
 */
@Service
public class BooktapServiceImplReader extends ServiceImpl<BooktapMapper, Booktap>
        implements BooktapServiceReader {

    @Autowired
    private BooktagMapper booktagMapper;
    @Autowired
    private BooktapMapper booktapMapper;
    @Override
    @Transactional
    public ResponseResult<String> addBookmark(String bookId, String value, String token) {
        //检测当前value是否有记录
        boolean newTag=false;
        String userId = JwtUtils.parseJWT(token);
        QueryWrapper<Booktag> queryWrapper_0=new QueryWrapper<>();
        int userIdInt = Integer.parseInt(userId);
        queryWrapper_0.eq("tag_value", value).eq("tag_delete", 0).and(wrapper-> wrapper.isNull("user_id").or().eq("user_id", userIdInt));
        Booktag booktag = booktagMapper.selectOne(queryWrapper_0);
        if(booktag==null){
            //存在则获取id，不存在创建，主键回填获取主键
            newTag=true;
            booktag=new Booktag();
            booktag.setTagValue(value);
            booktag.setUserId(userIdInt);
            if(booktagMapper.insert(booktag)==0){
                return ResponseResult.getErrorResult("添加自定义书签出错", StatusCode.NOT_MODIFIED,null);
            }
        }
        //添加书签
        Booktap bookTap=new Booktap();
        bookTap.setBookid(bookId);
        bookTap.setTapid(booktag.getTagId());
        bookTap.setUserid(userIdInt);
        if(booktapMapper.insert(bookTap)==0){
            return ResponseResult.getErrorResult("为图书添加书签出错", StatusCode.NOT_MODIFIED,null);
        }
        return ResponseResult.getSuccessResult(null, newTag?"当前添加书签不存在，以为您自动添加值为："+value+"的书签，书签添加成功":"书签添加成功");
    }
}




