package com.tcc.uffmaterias.domain.repository.redis;

import com.tcc.uffmaterias.domain.model.redis.UserRecoveryCode;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRecoveryCodeRepository extends CrudRepository<UserRecoveryCode,String> {
    Optional<UserRecoveryCode> findByEmail(String email);
}
