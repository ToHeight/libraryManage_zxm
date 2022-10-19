package sale.ljw.librarySystemReader.backend.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sale.ljw.librarySystemReader.backend.service.ConstantServiceReader;
import sale.ljw.librarySystemReader.backend.service.TypeServiceReader;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/dropdownListData")
public class RDropdownListDataServlet {
    @Autowired
    private TypeServiceReader typeServiceReader;

    @Autowired
    private ConstantServiceReader constantServiceReader;
    /**
     * 获取图书类别下拉列表
     * @return
     */
    @ApiOperation(value = "获取图书类别下拉列表")
    @GetMapping("/getBookType")
    public ArrayList<Map<String,Object>> getBookType(){
        return typeServiceReader.getBookType();
    }

    /**
     * 获取作者国家下拉列表
     * @return
     */
    @ApiOperation(value = "获取作者国家下拉列表")
    @GetMapping("/getAuthorCountry")
    public ArrayList<Map<String,Object>> getAuthorCountry(){
        return constantServiceReader.getAuthorCountry();
    }
    /**
     * 获取图书语言下拉列表
     * @return
     */
    @ApiOperation(value = "获取图书语言下拉列表")
    @GetMapping("/getBookLanguage")
    public ArrayList<Map<String,Object>> getBookLanguage(){
        return constantServiceReader.getBookLanguage();
    }

}
