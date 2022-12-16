package sale.ljw.librarySystemAdmin.backend.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.librarySystemAdmin.backend.service.AppointmentstimeServiceAdmin;
import sale.ljw.librarySystemAdmin.backend.service.ConstantServiceAdmin;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/dropdownListData")
public class ADropdownListDataServlet {
    @Autowired
    private sale.ljw.librarySystemAdmin.common.config.librarySystemReaderFeignClient librarySystemReaderFeignClient;
    @Autowired
    private ConstantServiceAdmin constantServiceAdmin;
    @Autowired
    private AppointmentstimeServiceAdmin appointmentstimeServiceAdmin;

    /**
     * 获取图书类别下拉列表
     *
     * @return
     */
    @ApiOperation(value = "获取图书类别下拉列表")
    @PreAuthorize("hasAnyAuthority('1') or hasAnyRole('1','2','3')")
    public ResponseResult<ArrayList<Map<String, Object>>> getBookType() {
        return librarySystemReaderFeignClient.getBookType();
    }

    /**
     * 获取图书状态下拉列表
     *
     * @return
     */
    @ApiOperation(value = "获取图书状态下拉列表")
    @PreAuthorize("hasAnyAuthority('1') or hasAnyRole('1','2','3')")
    @GetMapping("/getBookStatus")
    public ResponseResult<ArrayList<Map<String, Object>>> getBookStatus() {
        return constantServiceAdmin.getBookStatus();
    }

    /**
     * 获取作者国家下拉列表
     *
     * @return
     */
    @ApiOperation(value = "获取作者国家下拉列表")
    @PreAuthorize("hasAnyAuthority('1') or hasAnyRole('1','2','3')")
    @GetMapping("/getAuthorCountry")
    public ResponseResult<ArrayList<Map<String, Object>>> getAuthorCountry() {
        return librarySystemReaderFeignClient.getAuthorCountry();
    }

    /**
     * 获取图书语言下拉列表
     *
     * @return
     */
    @ApiOperation(value = "获取图书语言下拉列表")
    @PreAuthorize("hasAnyAuthority('1') or hasAnyRole('1','2','3')")
    @GetMapping("/getBookLanguage")
    public ResponseResult<ArrayList<Map<String, Object>>> getBookLanguage() {
        return librarySystemReaderFeignClient.getBookLanguage();
    }

    /**
     * 获取借阅状态
     *
     * @return
     */
    @ApiOperation(value = "获取借阅状态")
    @PreAuthorize("hasAnyAuthority('2') or hasAnyRole('1','2')")
    @GetMapping("/getBorrowStatus")
    public ResponseResult<ArrayList<Map<String, Object>>> getBorrowStatus() {
        return librarySystemReaderFeignClient.getBorrowStatus();
    }

    /**
     * 获取预定图书状态
     *
     * @return
     */
    @ApiOperation(value = "获取预定图书状态")
    @PreAuthorize("hasAnyAuthority('2') or hasAnyRole('1','2')")
    @GetMapping("/getPreOrderBookStatus")
    public ResponseResult<ArrayList<Map<String, Object>>> getPreOrderBookStatus() {
        return librarySystemReaderFeignClient.getPreOrderBookStatus();
    }

    /**
     * 活动类别
     *
     * @return
     */
    @ApiOperation(value = "活动类别")
    @PreAuthorize("hasAnyAuthority('5') or hasAnyRole('1','5')")
    @GetMapping("/getEventCategory")
    public ResponseResult<ArrayList<Map<String, Object>>> getEventCategory() {
        return librarySystemReaderFeignClient.getEventCategory();
    }

    /**
     * 获取活动状态
     * @return
     */
    @ApiOperation(value = "获取活动状态")
    @PreAuthorize("hasAnyAuthority('5') or hasAnyRole('1','5')")
    @GetMapping("/getActivityStatus")
    public ResponseResult<ArrayList<Map<String, Object>>> getActivityStatus() {
        return librarySystemReaderFeignClient.getActivityStatus();
    }

    /**
     * 获取楼层
     * @return
     */
    @ApiOperation(value = "获取楼层")
    @PreAuthorize("hasAnyAuthority('1') or hasAnyRole('1','2','3')")
    @GetMapping("/getFloor")
    public ResponseResult<ArrayList<Map<String, Object>>> getFloor() {
        return librarySystemReaderFeignClient.getFloor();
    }

    /**
     * 获取性别
     *
     * @return
     */
    @ApiOperation(value = "获取性别")
    @PreAuthorize("hasAnyAuthority('9') or hasAnyRole('1','4')")
    @GetMapping("/getGender")
    public ResponseResult<ArrayList<Map<String, Object>>> getGender() {
        return librarySystemReaderFeignClient.getGender();
    }

    /**
     * 查询时间列表
     *
     * @return
     */
    @ApiOperation(value = "查询时间列表")
    @PreAuthorize("hasAnyAuthority('6') or hasAnyRole('1','3')")
    @GetMapping("/queryTimeList")
    public ResponseResult<ArrayList<Map<String, Object>>> queryTimeList() {
        return appointmentstimeServiceAdmin.queryTimeList();
    }

    /**
     * 获取楼层状态
     * @return
     */
    @ApiOperation(value = "获取楼层状态")
    @PreAuthorize("hasAnyAuthority('6') or hasAnyRole('1','3')")
    @GetMapping("/findFloorStatus")
    public ResponseResult<ArrayList<Map<String, Object>>> findfloorStatus() {
        return constantServiceAdmin.findfloorStatus();
    }
}
