package gugunava.danil.usermanagementservice.config;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CachingConfig extends CachingConfigurerSupport {

}
