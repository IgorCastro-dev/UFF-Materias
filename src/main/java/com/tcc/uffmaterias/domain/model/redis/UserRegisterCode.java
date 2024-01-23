package com.tcc.uffmaterias.domain.model.redis;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("userRegisterCode")
public class UserRegisterCode {
    @Id
    private String id;

    @Indexed
    private String email;

    private String code;

    private LocalDateTime dateTime = LocalDateTime.now();

    @TimeToLive(unit = TimeUnit.MINUTES)
    private long expiration = 30;
}
