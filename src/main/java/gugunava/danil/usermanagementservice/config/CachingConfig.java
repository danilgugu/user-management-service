package gugunava.danil.usermanagementservice.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.SerializerConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.spring.cache.HazelcastCacheManager;
import gugunava.danil.usermanagementservice.cache.UserSerializer;
import gugunava.danil.usermanagementservice.model.User;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CachingConfig extends CachingConfigurerSupport {

    public static final String USERS = "users";
    private static final int ONE_HOUR_IN_SECONDS = 60 * 60;
    private static final int FOUR_HOURS_IN_SECONDS = 4 * ONE_HOUR_IN_SECONDS;

    @Bean
    @Override
    public CacheManager cacheManager() {
        HazelcastInstance client = hazelcastInstance();
        return new HazelcastCacheManager(client);
    }

    @Bean
    public HazelcastInstance hazelcastInstance() {
        return Hazelcast.newHazelcastInstance(createConfig());
    }

    private Config createConfig() {
        Config config = new Config();
        config.addMapConfig(mapConfig());
        config.getSerializationConfig().addSerializerConfig(serializerConfig());
        return config;
    }

    private SerializerConfig serializerConfig() {
        return new SerializerConfig().setImplementation(new UserSerializer()).setTypeClass(User.class);
    }

    private MapConfig mapConfig() {
        MapConfig mapConfig = new MapConfig(USERS);
        mapConfig.setTimeToLiveSeconds(ONE_HOUR_IN_SECONDS);
        mapConfig.setMaxIdleSeconds(FOUR_HOURS_IN_SECONDS);
        return mapConfig;
    }
}
