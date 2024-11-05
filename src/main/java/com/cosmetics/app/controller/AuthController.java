package com.cosmetics.app.controller;

import com.cosmetics.app.model.RefreshRequestParams;
import com.cosmetics.app.model.SignInRequest;
import com.cosmetics.app.model.TokensPair;
import com.cosmetics.app.model.UserRegistrationData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Аутентификация")
public interface AuthController {

    @Operation(summary = "Регистрация пользователя")
    @ApiResponse(responseCode = "200", description = "Пользователь успешно зарегистрирован")
    ResponseEntity<Void> signUp(@RequestBody @Valid UserRegistrationData request);

    @Operation(summary = "Авторизация пользователя")
    @ApiResponse(responseCode = "200", description = "Пользователь успешно авторизирован",
            content = @Content(schema = @Schema(implementation = TokensPair.class)))
    ResponseEntity<TokensPair> signIn(@RequestBody @Valid SignInRequest request);

    @Operation(summary = "Обновление рефреш токена")
    @ApiResponse(responseCode = "200", description = "Токен успешно обновлен",
            content = @Content(schema = @Schema(implementation = TokensPair.class)))
    ResponseEntity<TokensPair> refreshAuthToken(@RequestBody RefreshRequestParams body);
}
