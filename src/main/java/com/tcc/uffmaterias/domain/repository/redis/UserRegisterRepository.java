package com.tcc.uffmaterias.domain.repository.redis;

import com.tcc.uffmaterias.domain.model.redis.UserRegister;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRegisterRepository extends CrudRepository<UserRegister,String> {
    Optional<UserRegister> findByEmail(String email);
}
