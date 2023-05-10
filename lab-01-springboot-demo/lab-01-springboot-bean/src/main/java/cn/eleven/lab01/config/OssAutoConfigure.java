package cn.eleven.lab01.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

//@Component
//@Configuration
@EnableConfigurationProperties(FileServerProperties.class)
@Import({OosTemplate.class})
public class OssAutoConfigure {

}
