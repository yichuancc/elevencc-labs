package cn.eleven.lab01.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@ConditionalOnProperty(prefix = FileServerProperties.PREFIX + "." + FileServerProperties.TYPE_OOS, name = "enabled", havingValue = "true",matchIfMissing = true)
public class OosTemplate {

}
