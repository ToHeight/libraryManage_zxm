package sale.ljw.librarySystemAdmin.common.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import sale.ljw.common.common.http.ResponseResult;

import java.util.ArrayList;
import java.util.Map;

@FeignClient(value = "library-system-reader", configuration = FeignLogConfig.class)
public interface librarySystemReaderFeignClient {
    @GetMapping("/librarySystemReader/dropdownListData/getBookType")
    public ResponseResult<ArrayList<Map<String, Object>>> getBookType();

    @GetMapping("/librarySystemReader/dropdownListData/getAuthorCountry")
    public ResponseResult<ArrayList<Map<String, Object>>> getAuthorCountry();

    @GetMapping("/librarySystemReader/dropdownListData/getBookLanguage")
    public ResponseResult<ArrayList<Map<String, Object>>> getBookLanguage();

    @GetMapping("/librarySystemReader/dropdownListData/getBorrowStatus")
    public ResponseResult<ArrayList<Map<String, Object>>> getBorrowStatus();

    @GetMapping("/librarySystemReader/dropdownListData/getPreOrderBookStatus")
    public ResponseResult<ArrayList<Map<String, Object>>> getPreOrderBookStatus();

    @GetMapping("/librarySystemReader/dropdownListData/getEventCategory")
    public ResponseResult<ArrayList<Map<String, Object>>> getEventCategory();

    @GetMapping("/librarySystemReader/dropdownListData/getActivityStatus")
    public ResponseResult<ArrayList<Map<String, Object>>> getActivityStatus();

    @GetMapping("/librarySystemReader/dropdownListData/getFloor")
    public ResponseResult<ArrayList<Map<String, Object>>> getFloor();

    @GetMapping("/librarySystemReader/dropdownListData/getGender")
    public ResponseResult<ArrayList<Map<String, Object>>> getGender();

    @PostMapping("/librarySystemReader/lostBook/paymentConfirmation")
    public ResponseResult<String> paymentConfirmation(@RequestBody ArrayList<Integer> lostBookId);

    @GetMapping("/librarySystemReader/lostBook/pay/{payId}")
    public ResponseResult<String> payLostBook(@PathVariable String payId);
}
