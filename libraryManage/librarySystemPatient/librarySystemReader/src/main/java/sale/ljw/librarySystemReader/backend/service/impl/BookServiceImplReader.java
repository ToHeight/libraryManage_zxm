package sale.ljw.librarySystemReader.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sale.ljw.backend.form.FindBookByCriteria;
import sale.ljw.backend.dao.BookMapper;
import sale.ljw.backend.pojo.Book;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.common.common.http.StatusCode;
import sale.ljw.librarySystemReader.backend.service.BookServiceReader;

import java.util.List;
import java.util.Map;

/**
 * @author 86155
 * @description 针对表【Book】的数据库操作Service实现
 * @createDate 2022-10-17 16:14:59
 */
@Service
public class BookServiceImplReader extends ServiceImpl<BookMapper, Book>
        implements BookServiceReader {
    @Autowired
    private BookMapper bookMapper;

    /**
     * 根据条件查询图书信息
     * @param findBookByCriteria
     * @return
     */
    @Override
    public ResponseResult<PageInfo<Map<String,Object>>> findBookByCriteria(FindBookByCriteria findBookByCriteria) {
        //分页
        PageHelper.startPage(findBookByCriteria.getPage(), findBookByCriteria.getPageSize());
        //数据查询出来进行封装
        PageInfo<Map<String,Object>> pageInfo=new PageInfo<Map<String,Object>>(bookMapper.findBookByCriteria(findBookByCriteria));
        return ResponseResult.getSuccessResult(pageInfo, StatusCode.SUCCESS);
    }

}




