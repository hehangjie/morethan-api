package com.morethan.game.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * 类简单作用描述
 *
 * @Description: 类作用描述
 * @Author: Anthony
 * @CreateDate: 2018/4/24
 * @UpdateUser: Anthony
 * @UpdateDate: 2018/4/24 2:57 PM
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
@EnableSwagger2
@Configuration
public class Swagger2 {

	@Bean
    public Docket createRestApi() {
		String basePackage = "com.morethan.game.api";

        //添加head参数start
        List<Parameter> pars = new ArrayList<Parameter>();

        ParameterBuilder tokenPar = new ParameterBuilder();
        ParameterBuilder keyPar = new ParameterBuilder();
        ParameterBuilder timePar = new ParameterBuilder();
        ParameterBuilder signPar = new ParameterBuilder();
        tokenPar.name("x-access-token").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        keyPar.name("x-access-key").description("密钥Key").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        timePar.name("x-access-timestamp").description("时间戳").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        signPar.name("x-access-sign").description("签名").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());
        pars.add(keyPar.build());
        pars.add(timePar.build());
        pars.add(signPar.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build().globalOperationParameters(pars);

    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Morethan Tech APIs")
                .termsOfServiceUrl("http://www.morethantech.cn/")
                .version("1.0")
                .build();
    }

}
