package sale.ljw.librarySystemAdmin.common.config.basicConfiguration;

import com.obs.services.ObsClient;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author Lenovo
 */
@Configuration
@Data
public class HuaweiObsConfig {

    @Value("${huawei.obs.ak}")
    private String ak;

    @Value("${huawei.obs.sk}")
    private String sk;

    @Value("${huawei.obs.upload.endPoint}")
    private String endPoint;

    public ObsClient getInstance(){
        return new ObsClient(ak,sk,endPoint);
    }

}

