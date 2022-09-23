package com.ng.crud.h2.db.api.config.swagger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig extends WebMvcConfigurationSupport {

	@Autowired
	private BuildProperties buildProperties;

	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.ant("/rest/*")).build().apiInfo(metaData());
	}

	private ApiInfo metaData() {
		String apiInfoStr = "API Group Name : %s <br>API Build Date : %s <br>API Version : %s <br>";
		String apiBuildInfo = String.format(apiInfoStr, buildProperties.getGroup(), buildProperties.getTime(),
				buildProperties.getVersion());
		Contact contact = new Contact(buildProperties.get("api-contact-name"), buildProperties.get("api-contact-url"),
				buildProperties.get("api-contact-email"));
		return new ApiInfoBuilder().title(buildProperties.getName().toUpperCase().replace("-", " "))
				.description(apiBuildInfo).version(buildProperties.getVersion())
				.license(buildProperties.get("api-license")).licenseUrl(buildProperties.get("api-licenseUrl"))
				.contact(contact).build();
	}

	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");

		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

}
