package com.cosmetics.app.service;

import com.cosmetics.app.entity.RefreshToken;
import com.cosmetics.app.entity.User;

public interface UserTokensService {

    Long save(RefreshToken userTokens);

    void delete(RefreshToken userTokens);

    void deleteByID(Long id);

    RefreshToken getUserTokensByUserAndRefreshToken(User user, String refreshToken);
}
