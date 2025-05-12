package kz.anilenses.productservice.controller;

import jakarta.validation.Valid;
import kz.anilenses.productservice.dto.ProductUpsert;
import kz.anilenses.productservice.service.ProductServiceFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductServiceFactory serviceFactory;

    @PostMapping
    public void saveProduct(@RequestBody @Valid ProductUpsert request) {
        serviceFactory.getService(request.getCategory()).upsert(request);
    }

}
