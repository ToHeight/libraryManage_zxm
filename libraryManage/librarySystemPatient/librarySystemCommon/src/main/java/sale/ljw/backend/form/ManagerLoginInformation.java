package sale.ljw.backend.form;

import lombok.Data;

import java.util.LinkedList;

/**
 * <p>application name：librarySystemPatient - ManagerLoginInformation</p>
 * <p>application describing： </p>
 * <p>copyright： </p>
 * <p>company： </p>
 * <p>time：2022-12-14 17:12:17</p>
 *
 * @author liujingwen
 * @version ver 1.0
 * @since 1.8
 */
@Data
public class ManagerLoginInformation {
    private String id;
    private String username;
    private String password;
    private Integer roleId;
    private LinkedList<Integer> modelId;
}
