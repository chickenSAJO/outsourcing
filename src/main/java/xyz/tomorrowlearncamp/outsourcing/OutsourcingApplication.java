package xyz.tomorrowlearncamp.outsourcing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@SpringBootApplication
public class OutsourcingApplication {

    public static void main(String[] args) {
        SpringApplication.run(OutsourcingApplication.class, args);
    }

}
