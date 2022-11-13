package sale.ljw.librarySystemAdmin.backend.api;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sale.ljw.backend.form.BookTagList;
import sale.ljw.backend.form.EditBookTag;
import sale.ljw.backend.form.FindAllBookTag;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.librarySystemAdmin.backend.service.BooktagServiceAdmin;

import javax.validation.Valid;

@RestController
@RequestMapping("/bookTagByAdmin")
public class ABookTagServlet {
    @Autowired
    private BooktagServiceAdmin booktagServiceAdmin;

    /**
     * 查询全部书签
     * @param bookTag
     * @return
     */
    @ApiOperation(value = "查询全书签")
    @PostMapping("/findAllBookTag")
    public ResponseResult<PageInfo<BookTagList>> findAllBookTag(@RequestBody @Valid FindAllBookTag bookTag){
        return booktagServiceAdmin.findAllBookTag(bookTag);
    }

    /**
     * 修改书签
     * @param bookTag
     * @return
     */
    @ApiOperation(value ="修改书签")
    @PostMapping("/editBookTag")
    public ResponseResult<String> editBookTag(@RequestBody @Valid EditBookTag bookTag){
        return booktagServiceAdmin.editBookTag(bookTag);
    }

    /**
     * 删除书签
     * @param tagId
     * @return
     */
    @ApiOperation(value = "删除书签")
    @DeleteMapping("/deleteTagById/{tagId}")
    public ResponseResult<String> deleteTagById(@PathVariable Integer tagId){
        return booktagServiceAdmin.deleteTagById(tagId);
    }

    /**
     * 新增书签
     * @param bookTag
     * @return
     */
    @ApiOperation(value = "新增书签")
    @PostMapping("/addBookTag")
    public ResponseResult<String> addBookTag(@RequestBody @Valid EditBookTag bookTag){
        return booktagServiceAdmin.addBookTag(bookTag);
    }
}
