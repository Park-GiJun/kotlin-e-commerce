package com.gijun.kotlinecommerce.infrastructure.config

import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.codec.JsonJacksonCodec
import org.redisson.config.Config
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CacheLockConfig(
    @param:Value("\${spring.data.redis.host}")
    private val redisHost: String,

    @param:Value("\${spring.data.redis.port}")
    private val redisPort: Int,

    @param:Value("\${spring.data.redis.password}")
    private val redisPassword: String
) {
    @Bean
    fun redissonClient(): RedissonClient {
        val config = Config()
        config.codec = JsonJacksonCodec()
        config.useSingleServer()
            .setAddress("redis://$redisHost:$redisPort")
            .password = redisPassword
        return Redisson.create(config)
    }
}