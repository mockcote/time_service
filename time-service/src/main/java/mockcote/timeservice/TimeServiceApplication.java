package mockcote.timeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class TimeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TimeServiceApplication.class, args);
    }

}
