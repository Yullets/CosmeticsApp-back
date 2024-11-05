package com.cosmetics.app.repository;

import com.cosmetics.app.entity.RefreshToken;
import com.cosmetics.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserTokensRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByUserAndRefreshToken(User user, String refreshToken);

    void deleteById(Long id);
}
