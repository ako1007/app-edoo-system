package it.city.appedooserver;

import it.city.appedooserver.config.InitConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2

public class AppEdooServerApplication {


        public static void main(String[] args) {
            if (InitConfig.isStart()) {
                SpringApplication.run(AppEdooServerApplication.class, args);
            } else System.out.println("Malumotlar o'chmadi");
        }


}
