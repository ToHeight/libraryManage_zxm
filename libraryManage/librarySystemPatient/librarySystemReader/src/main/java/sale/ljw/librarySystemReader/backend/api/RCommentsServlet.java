package sale.ljw.librarySystemReader.backend.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sale.ljw.backend.form.BookMessage;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.librarySystemReader.backend.service.CommentsServiceReader;

import javax.validation.Valid;

@RestController
@RequestMapping("/commentsByReader")
public class RCommentsServlet {
    @Autowired
    private CommentsServiceReader commentsServiceReader;

    /**
     * 查找图书留言
     * @param bookMessage
     * @param token
     * @return
     */
    @PostMapping("/bookMessage")
    public ResponseResult<String> bookMessage(@RequestBody @Valid BookMessage bookMessage, @RequestHeader(name = "token") String token){
        return commentsServiceReader.bookMessage(bookMessage,token);
    }

}
