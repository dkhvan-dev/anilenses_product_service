package kz.anilenses.productservice.service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import kz.anilenses.productservice.types.ProductCategoryEnum;
import kz.anilenses.productservice.types.ProductInterface;
import kz.anilenses.exceptionhandler.LocalizedException;
import kz.anilenses.productservice.dto.ProductUpsert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static kz.anilenses.productservice.enums.ProductErrorCode.PRODUCT_CATEGORY_NOT_FOUND;

@Component
public class ProductServiceFactory {

    private final Map<ProductCategoryEnum, ProductService<? extends ProductUpsert, ? extends ProductInterface>> products = new EnumMap<>(ProductCategoryEnum.class);

    @Autowired
    public ProductServiceFactory(List<ProductService<? extends ProductUpsert, ? extends ProductInterface>> productServices) {
        for (ProductService<? extends ProductUpsert, ? extends ProductInterface> productService : productServices) {
            products.put(productService.getCategory(), productService);
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends ProductUpsert, V extends ProductInterface> ProductService<T, V> getService(ProductCategoryEnum category) {
        var productService = products.get(category);

        if (productService == null) {
            throw new LocalizedException(PRODUCT_CATEGORY_NOT_FOUND);
        }

        return (ProductService<T, V>) productService;
    }

}
