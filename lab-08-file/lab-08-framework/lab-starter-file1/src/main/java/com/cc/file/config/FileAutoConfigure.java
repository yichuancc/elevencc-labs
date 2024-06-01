package com.cc.file.config;

import com.cc.file.properties.FileServerProperties;
import com.cc.file.service.impl.FileServiceComponentScan;
import com.cc.file.template.FtpTemplate;
import com.cc.file.template.MinIoTemplate;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
@EnableConfigurationProperties(FileServerProperties.class)
@Import({MinIoTemplate.class, FtpTemplate.class, FileServiceComponentScan.class})
public class FileAutoConfigure {

}
