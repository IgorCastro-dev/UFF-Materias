package com.tcc.uffmaterias.domain.model.redis;

import com.tcc.uffmaterias.domain.model.jpa.UsuarioTipo;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.util.concurrent.TimeUnit;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("userRegister")
public class UserRegister {

    @Id
    private String id;

    private String nome;

    @Indexed
    private String email;

    private String celular;

    private String password;

    @TimeToLive(unit = TimeUnit.MINUTES)
    private long expiration = 30;
}
