package sale.ljw.librarySystemAdmin.backend.api;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sale.ljw.backend.form.DeleteComments;
import sale.ljw.backend.form.QueryMessages;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.librarySystemAdmin.backend.service.CommentsServiceAdmin;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/commentsByAdmin")
public class ACommentsServlet {
    @Autowired
    private CommentsServiceAdmin commentsServiceAdmin;

    /**
     * 查询全部留言
     * @param queryMessages
     * @return
     */
    @ApiOperation(value = "查询全部留言")
    @PreAuthorize("hasAnyAuthority('2') or hasAnyRole('1','2')")
    @PostMapping("/queryAllMessages")
    public ResponseResult<PageInfo<Map<String, Object>>> queryAllMessages(@RequestBody @Valid QueryMessages queryMessages) {
        return commentsServiceAdmin.queryAllMessages(queryMessages);
    }

    /**
     * 删除留言
     * @param deleteComments
     * @return
     */
    @ApiOperation(value = "删除留言")
    @PreAuthorize("hasAnyAuthority('2') or hasAnyRole('1','2')")
    @PostMapping("/deleteComments")
    public ResponseResult<String> deleteComments(@RequestBody @Valid DeleteComments deleteComments){
        return commentsServiceAdmin.deleteComments(deleteComments);
    }
}
