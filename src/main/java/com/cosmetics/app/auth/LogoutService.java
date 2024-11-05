package com.cosmetics.app.auth;

import com.cosmetics.app.service.JwtService;
import com.cosmetics.app.service.UserTokensService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String HEADER_NAME = "Authorization";

    private final JwtService jwtService;
    private final UserTokensService userTokensService;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        var authHeader = request.getHeader(HEADER_NAME);
        if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, BEARER_PREFIX)) {
            return;
        }

        var jwt = authHeader.substring(BEARER_PREFIX.length());
        Long refreshTokenID =  jwtService.extractRefreshID(jwt);

        userTokensService.deleteByID(refreshTokenID);
    }
}
