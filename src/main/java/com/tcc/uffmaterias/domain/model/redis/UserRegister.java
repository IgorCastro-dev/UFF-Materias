package com.tcc.uffmaterias.domain.model.redis;

import com.tcc.uffmaterias.domain.model.jpa.UsuarioTipo;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

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

    private UsuarioTipo usuarioTipo;
}
