package com;

import com.springapi.config.MybatisInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@MapperScan(value = "com.springapi.mapper")
@ServletComponentScan
public class DemoApplication {

	public static void main(String[] args) {

		SpringApplication.run(DemoApplication.class, args);

	}

	@Bean
	public MybatisInterceptor interceptor() {
		return new MybatisInterceptor();
	}

	public void addCorsMappings(CorsRegistry registry) {

		registry.addMapping("/**")
				.allowCredentials(true)
				.allowedHeaders("*")
				.allowedOrigins("*")
				.allowedMethods("*");
	}


}
