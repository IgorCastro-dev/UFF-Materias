package com.tcc.uffmaterias.domain.repository.redis;

import com.tcc.uffmaterias.domain.model.redis.UserRegisterCode;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRegisterCodeRepository extends CrudRepository<UserRegisterCode,String> {
    Optional<UserRegisterCode> findByEmail(String email);
}
