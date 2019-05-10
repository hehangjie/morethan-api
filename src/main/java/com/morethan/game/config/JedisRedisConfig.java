package com.morethan.game.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class JedisRedisConfig {

	@Value("${spring.redis.host}")
    private  String host;
	
	@Value("${spring.redis.password}")
    private  String password;
    
	@Value("${spring.redis.port}")
    private  int port;
    
	@Value("${spring.redis.timeout}")
    private  int timeout;
    
	@Value("${spring.redis.database}")
    private  int database;
    
	@Value("${spring.redis.jedis.pool.max-active}")
    private  int maxWait;
    
	@Value("${spring.redis.jedis.pool.max-idle}")
    private  int maxIdle;
    
	@Value("${spring.redis.jedis.pool.min-idle}")
    private  int minIdle;
    
    @Bean(name = "redisTemplate")
    public RedisTemplate<String, String> redisTemplate() {
        return new StringRedisTemplate(jedisConnectionFactory());
    }
    
	@Bean
    JedisConnectionFactory jedisConnectionFactory() {
		RedisStandaloneConfiguration configuration =new RedisStandaloneConfiguration(host, port);
		configuration.setPassword(RedisPassword.of(password));
		configuration.setDatabase(database);
        JedisConnectionFactory factory = new JedisConnectionFactory(configuration);
        return factory;
    }
	
	@Bean  
    public JedisPool redisPoolFactory() {  
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();  
        jedisPoolConfig.setMaxIdle(maxIdle);  
        jedisPoolConfig.setMaxWaitMillis(maxWait);  
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMinIdle(minIdle);
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout);  
  
        return jedisPool;  
    }  
}
