package com.lots.lotswxxw.config.MvcConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName WebMvcConfig
 * @Description TODO
 * @Author lots
 * @Date 2019/4/29 19:22
 * @Version V1.0
 **/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * @param registry registry
     * @author xiaobu
     * @date 2019/1/18 13:51
     * @descprition 等价于 http://localhost:9001/1.txt 依次在static upload目录下找1.txt文件
     * @version 1.0
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

}