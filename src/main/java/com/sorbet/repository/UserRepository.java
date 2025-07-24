package com.sorbet.repository;

import com.sorbet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByUserLoginId(String userId);
    boolean existsByUserLoginId(String userId);
    Optional<User> findByNickname(String nickname);

}


