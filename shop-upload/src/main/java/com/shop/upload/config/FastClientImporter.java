package com.shop.upload.config;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;

@Configuration
@Import(FdfsClientConfig.class) //读取配置文件 已在application配置
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING) //解决重复注册bean问题
public class FastClientImporter {
}
