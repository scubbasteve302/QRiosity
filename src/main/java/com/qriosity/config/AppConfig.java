package com.qriosity.config;

import java.util.ArrayList;
import java.util.List;

import com.qriosity.servlet.filter.JsonpFilter;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.JacksonObjectMapperFactoryBean;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

/**
 * @author yoandy
 * @since 10/5/13
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.qriosity.mvc.controller","com.qriosity.service"})
public class AppConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("/WEB-INF/styles/").setCachePeriod(0);
        registry.addResourceHandler("/img/**").addResourceLocations("/WEB-INF/img/").setCachePeriod(0);
        registry.addResourceHandler("/js/**").addResourceLocations("/WEB-INF/js/").setCachePeriod(0);
        registry.addResourceHandler("**").addResourceLocations("/WEB-INF/").setCachePeriod(0);
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(jacksonHttpMessageConverter());
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
    }

    @Bean
    public ViewResolver viewResolver() {
        UrlBasedViewResolver bean = new UrlBasedViewResolver();
        bean.setPrefix("/");
        bean.setSuffix(".html");
        bean.setViewClass(JstlView.class);
        return bean;
    }

    @Bean
    public MappingJacksonHttpMessageConverter jacksonHttpMessageConverter() {
        MappingJacksonHttpMessageConverter jacksonHttpMessageConverter = new MappingJacksonHttpMessageConverter();
        jacksonHttpMessageConverter.setPrettyPrint(Boolean.TRUE);
        jacksonHttpMessageConverter.setObjectMapper(jacksonObjectMapperFactoryBean().getObject());
        return jacksonHttpMessageConverter;
    }

    @Bean
    public JacksonObjectMapperFactoryBean jacksonObjectMapperFactoryBean() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
        JacksonObjectMapperFactoryBean jacksonObjectMapperFactoryBean= new JacksonObjectMapperFactoryBean();
        jacksonObjectMapperFactoryBean.setObjectMapper(mapper);

        return jacksonObjectMapperFactoryBean;
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
        configureMessageConverters(converters);
        restTemplate.setMessageConverters(converters);
        return restTemplate;
    }

    @Bean
    public JsonpFilter jsonpFilter() {
        return new JsonpFilter();
    }
}
