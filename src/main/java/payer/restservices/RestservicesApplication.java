package payer.restservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import payer.restservices.filter.CORSFilter;

@SpringBootApplication
@EnableCaching
public class RestservicesApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(RestservicesApplication.class, args);
	}
	
//	
//	@Bean
//	public FilterRegistrationBean<CORSFilter> corsFilterRegistration() {
//		
//		FilterRegistrationBean<CORSFilter> registrationBean= new FilterRegistrationBean<CORSFilter>(new CORSFilter());
//		registrationBean.setName("CORS Filter");
//		registrationBean.addUrlPatterns("/*");
//		registrationBean.setOrder(1);
//		return registrationBean;
//	}
	
	
	
	/*
	 * @Bean public WebMvcConfigurer corsConfigurer() { return new
	 * WebMvcConfigurer() {
	 * 
	 * @Override public void addCorsMappings(CorsRegistry registry) {
	 * registry.addMapping("/greeting-javaconfig").allowedOrigins(
	 * "http://localhost:8080"); } }; }
	 */

}
