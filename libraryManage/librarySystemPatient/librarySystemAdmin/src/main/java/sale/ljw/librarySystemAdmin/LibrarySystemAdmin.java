package sale.ljw.librarySystemAdmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class LibrarySystemAdmin {
    public static void main(String[] args) {
        SpringApplication.run(LibrarySystemAdmin.class, args);
    }
}
