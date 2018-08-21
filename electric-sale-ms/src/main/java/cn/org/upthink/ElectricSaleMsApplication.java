package cn.org.upthink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
@SpringBootApplication
public class ElectricSaleMsApplication{
    public static void main(String[] args) {
        new SpringApplicationBuilder(ElectricSaleMsApplication.class).web(WebApplicationType.SERVLET).run(args);
    }


}
