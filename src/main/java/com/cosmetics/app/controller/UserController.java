package com.cosmetics.app.controller;

import com.cosmetics.app.model.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Данные пользователя")
public interface UserController {

    @Operation(summary = "Вывод профиля пользователя")
    @ApiResponse(responseCode = "200", description = "Данные профиля успешно получены.",
            content = @Content(schema = @Schema(implementation = UserDto.class)))
    ResponseEntity<UserDto> getProfileData();
}
