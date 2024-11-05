package com.cosmetics.app.service;

import com.cosmetics.app.model.ProductDto;

import java.util.List;

public interface ProductService {

    List<ProductDto> findAll();
}
