package com.cosmetics.app.service.mapper;

import com.cosmetics.app.entity.Product;
import com.cosmetics.app.model.ProductDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto toDto(Product product);

    List<ProductDto> toListDto(List<Product> products);
}
