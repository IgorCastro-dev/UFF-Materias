package com.tcc.uffmaterias.domain.model.redis;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("userRecoveryCode")
public class UserRecoveryCode {

    @Id
    private String id;

    @Indexed
    private String email;

    private String code;

    private LocalDateTime dateTime = LocalDateTime.now();
}
