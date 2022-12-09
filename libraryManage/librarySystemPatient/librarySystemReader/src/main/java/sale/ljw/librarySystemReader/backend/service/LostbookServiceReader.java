package sale.ljw.librarySystemReader.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import sale.ljw.backend.pojo.Lostbook;
import sale.ljw.common.common.http.ResponseResult;

import java.util.ArrayList;
import java.util.Map;

/**
* @author 86155
* @description 针对表【lostBook】的数据库操作Service
* @createDate 2022-12-05 18:22:23
*/
public interface LostbookServiceReader extends IService<Lostbook> {

    ResponseResult<String> addLostBook(Integer borrowId, String token);

    ResponseResult<String> paymentConfirmation(ArrayList<Integer> borrowId);

    ResponseResult<PageInfo<Map<String, Object>>> findAllLostBook(Integer page,String token);

    void scanPicture(String payId);

    ResponseResult<String> payLostBook(String payId);

    void updateBorrowLost();
}
