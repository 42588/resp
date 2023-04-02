package cn.hbmz.ruiji.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import cn.hbmz.ruiji.common.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    /**
     * 设置静态资源映射
     * 
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("开始进行静态资源映射...");
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
    }

    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("拓展消息转化器");
        // 创建消息转换器对象
        MappingJackson2HttpMessageConverter messageConverters = new MappingJackson2HttpMessageConverter();
        // 设置消息转换器对象
        messageConverters.setObjectMapper(new JacksonObjectMapper());
        // 将我们的消息转换器添加到Java Springmvc的框架之中
        converters.add(0, messageConverters);
    }

}
