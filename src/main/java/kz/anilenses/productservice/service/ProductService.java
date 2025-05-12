package kz.anilenses.productservice.service;

import graphql.schema.SelectedField;
import java.util.List;
import kz.anilenses.productservice.types.PageableInput;
import kz.anilenses.productservice.types.ProductCategoryEnum;

public interface ProductService<T, V> {

    void upsert(T request);

    V findAllPageable(PageableInput pageable, List<SelectedField> fields);

    ProductCategoryEnum getCategory();

}
