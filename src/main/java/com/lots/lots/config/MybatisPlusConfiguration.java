package com.lots.lots.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * MyBatis Plus参数配置
 * http://mp.baomidou.com
 * 
 * @author lots
 * @version 1.0.0 2022-03-24
 */
@Configuration
@MapperScan({"com.lots.lots.dao.mapper"})
public class MybatisPlusConfiguration {
    /**
     * 初始化分页插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    /**
     * 初始化公共字段自动填充功能
     */
    @Component
    public class MetaObjectHandlerConfiguration implements MetaObjectHandler {
        @Override
        public void insertFill(MetaObject metaObject) {
            Date today = new Date();
            this.fillStrategy(metaObject, "createDate", today);
            this.fillStrategy(metaObject, "updateDate", today);
        }

        @Override
        public void updateFill(MetaObject metaObject) {
            this.fillStrategy(metaObject, "updateDate", new Date());
        }
    }
}
