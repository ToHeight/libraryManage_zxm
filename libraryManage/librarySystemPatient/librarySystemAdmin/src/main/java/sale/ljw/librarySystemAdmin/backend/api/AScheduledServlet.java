package sale.ljw.librarySystemAdmin.backend.api;

import com.github.pagehelper.PageInfo;
import com.netflix.discovery.converters.Auto;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sale.ljw.backend.form.ModifyReservationStatus;
import sale.ljw.backend.form.QueryPreOrderedBook;
import sale.ljw.common.common.http.ResponseResult;
import sale.ljw.librarySystemAdmin.backend.service.ScheduledServiceAdmin;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/scheduledByAdmin")
public class AScheduledServlet {

    @Autowired
    private ScheduledServiceAdmin scheduledServiceAdmin;

    /**
     * 获取全部预定图书
     * @param book
     * @return
     */
    @ApiOperation(value = "获取全部预定图书")
    @PreAuthorize("hasAnyAuthority('2') or hasAnyRole('1','2')")
    @PostMapping("/getAllPreOrderedBook")
    public ResponseResult<PageInfo<Map<String,Object>>> getAllPreOrderedBook(@RequestBody @Valid QueryPreOrderedBook book){
        return scheduledServiceAdmin.getAllPreOrderedBook(book);
    }

    /**
     * 修改预定状态
     * @param modifyReservationStatus
     * @return
     */
    @ApiOperation(value = "修改预定状态")
    @PreAuthorize("hasAnyAuthority('2') or hasAnyRole('1','2')")
    @PostMapping("/modifyReservationStatus")
    public ResponseResult<String> modifyReservationStatus(@RequestBody @Valid ModifyReservationStatus modifyReservationStatus){
        return scheduledServiceAdmin.modifyReservationStatus(modifyReservationStatus);
    }

}
