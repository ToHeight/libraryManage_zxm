package sale.ljw.librarySystemReader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class LibrarySystemReader {
    public static void main(String[] args) {
        SpringApplication.run(LibrarySystemReader.class,args);
    }
}
