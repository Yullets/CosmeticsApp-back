package com.cosmetics.app.controller;

import com.cosmetics.app.model.ProductDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Продукция")
public interface ProductController {

    @Operation(summary = "Получение списка продуктов")
    @ApiResponse(responseCode = "200", description = "Стажер успешно найден",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductDto.class))))
    ResponseEntity<List<ProductDto>> getAllProducts();
}
