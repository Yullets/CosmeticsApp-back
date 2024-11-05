package com.cosmetics.app.service.impl;

import com.cosmetics.app.entity.RefreshToken;
import com.cosmetics.app.entity.User;
import com.cosmetics.app.exception.NotFoundException;
import com.cosmetics.app.repository.UserTokensRepository;
import com.cosmetics.app.service.UserTokensService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserTokensServiceImpl implements UserTokensService {

    private final UserTokensRepository userTokensRepository;

    @Override
    public Long save(RefreshToken userTokens) {
        return userTokensRepository.save(userTokens).getId();
    }

    @Override
    public void delete(RefreshToken userTokens) {
        userTokensRepository.delete(userTokens);
    }

    @Override
    public void deleteByID(Long id) {
        userTokensRepository.deleteById(id);
    }

    @Override
    public RefreshToken getUserTokensByUserAndRefreshToken(User user, String refreshToken) {
        return userTokensRepository.findByUserAndRefreshToken(user, refreshToken)
                .orElseThrow(() -> new NotFoundException("UserToken not found"));
    }
}
