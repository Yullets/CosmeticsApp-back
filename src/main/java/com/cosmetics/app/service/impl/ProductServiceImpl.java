package com.cosmetics.app.service.impl;

import com.cosmetics.app.model.ProductDto;
import com.cosmetics.app.repository.ProductRepository;
import com.cosmetics.app.service.ProductService;
import com.cosmetics.app.service.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public List<ProductDto> findAll() {
        return productMapper.toListDto(productRepository.findAll());
    }
}
