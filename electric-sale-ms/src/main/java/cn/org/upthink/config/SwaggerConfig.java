package cn.org.upthink.config;


import cn.org.upthink.model.swagger.SwaggerApiInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.LinkedList;
import java.util.List;

@EnableSwagger2
@Configuration
public class SwaggerConfig extends SwaggerTemplate {

    @Bean
    public SwaggerApiInfo info() {
        return SwaggerApiInfo.builder().title("elec-service").version("v1").serviceUrl(null).build();
    }
/*
    private List<ResponseMessage> extractStatusCodes() {
        final LinkedList<ResponseMessage> list = new LinkedList<>();
        for (StatusCode statusCodes : StatusCode.values()) {
            final ResponseMessageBuilder builder = new ResponseMessageBuilder();
            final ResponseMessage message = builder
                    .code(statusCodes.code())
                    .message(statusCodes.message())
                    .build();
            list.add(message);
        }
        return list;
    }*/

}
