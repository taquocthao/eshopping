package com.tathao.eshopping.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;

@Configuration
@ComponentScan("com.tathao.eshopping.*")
public class ApplicationContextConfig extends ApplicationObjectSupport{
	
	@Bean(name = "viewResolver")
	public  UrlBasedViewResolver getViewResolver() {
		UrlBasedViewResolver viewResolver = new UrlBasedViewResolver();
		
		viewResolver.setViewClass(TilesView.class);
		viewResolver.setOrder(0);
		
		return viewResolver;
	}
	
	@Bean(name = "viewResolver2")
	public InternalResourceViewResolver getViewResolver2() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		
		viewResolver.setPrefix("/WEB-INF/view");
		viewResolver.setSuffix(".jsp");
		viewResolver.setOrder(2);
		
		return viewResolver;
	}

	@Bean(name = "tilesConfigurer")
    public TilesConfigurer getTilesConfigurer() {
        TilesConfigurer tilesConfigurer = new TilesConfigurer();
 
        tilesConfigurer.setDefinitions("/WEB-INF/tiles.xml");
 
        return tilesConfigurer;
    }
	
	@Bean(name="messageSource")
	public MessageSource getMessageResource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		
		messageSource.setBasename("classpath:i18n/resource");
		messageSource.setDefaultEncoding("UTF-8");
		
		return messageSource;
	}
	
	@Bean(name="localeResolver")
	public LocaleResolver getLocalResolver() {
		CookieLocaleResolver resolver = new CookieLocaleResolver();
		resolver.setCookieDomain("myAppLocaleCookie");
		resolver.setCookieMaxAge(60*60);
		return resolver;
	}
	
}
