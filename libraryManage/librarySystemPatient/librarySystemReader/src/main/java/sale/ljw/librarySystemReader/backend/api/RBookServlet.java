package sale.ljw.librarySystemReader.backend.api;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sale.ljw.backend.form.FindBookByCriteria;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.librarySystemReader.backend.service.BookServiceReader;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/bookByReader")
public class RBookServlet {
    @Autowired
    private BookServiceReader bookServiceReader;

    /**
     * 根据条件查询图书
     * @param findBookByCriteria
     * @return
     */
    @ApiOperation(value = "根据条件查找图书")
    @PostMapping(path = "/findBookByCriteria")
    public ResponseResult<PageInfo<Map<String,Object>>> findBookByCriteria(@RequestBody @Valid FindBookByCriteria findBookByCriteria) {
        return bookServiceReader.findBookByCriteria(findBookByCriteria);
    }


}
