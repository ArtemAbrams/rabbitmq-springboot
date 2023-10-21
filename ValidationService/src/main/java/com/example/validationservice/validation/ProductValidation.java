package com.example.validationservice.validation;

import com.example.validationservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductValidation {
    private final ProductRepository productRepository;
    public boolean isProductExist(String name){
        return productRepository.findProductByName(name)
                .isPresent();
    }

}
