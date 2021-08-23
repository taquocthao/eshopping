package com.tathao.eshopping.config;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer{
	
	private static final Charset UTF8 = Charset.forName("UTF-8");

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/css/**").addResourceLocations("/asset/css/");
		registry.addResourceHandler("/img/**").addResourceLocations("/asset/images/");
		registry.addResourceHandler("/js/**").addResourceLocations("/asset/js/");
		registry.addResourceHandler("/fonts/**").addResourceLocations("/asset/fonts/");
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		
		registry.addInterceptor(localeChangeInterceptor).addPathPatterns("/*");
	}

//	@Override
//	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
//		configurer
//				.favorPathExtension(true)
//				.favorParameter(true)
//				.defaultContentType(MediaType.TEXT_HTML)
//				.mediaType("html", MediaType.TEXT_HTML)
//				.mediaType("xml", MediaType.APPLICATION_XML)
//				.mediaType("json", MediaType.APPLICATION_JSON);
//	}
}
