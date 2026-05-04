package com.example.demo.repositries;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.models.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	// ログイン処理（U-AUTH-02）で使用するためにemailで検索
    Optional<UserEntity> findByEmail(String email);
    boolean existsByEmail(String email);
}
