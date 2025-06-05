package edu.fdu.ivos.base.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("edu.fdu.ivos.*.mapper")
public class MybatisConfig {
}