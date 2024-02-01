package com.aspen.core.service.config;

import com.mybatisflex.core.mybatis.FlexConfiguration;
import com.mybatisflex.spring.boot.ConfigurationCustomizer;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis-Flex sql日志打印
 * @author jingchuan
 */
@Configuration
public class MyBatisFlexConfigurationCustomizer implements ConfigurationCustomizer {

    @Override
    public void customize(FlexConfiguration configuration) {
        configuration.setLogImpl(StdOutImpl.class);
    }
}