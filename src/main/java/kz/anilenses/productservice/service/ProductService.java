package kz.anilenses.productservice.service;

import graphql.schema.SelectedField;
import java.util.List;
import kz.anilenses.productservice.types.ProductCategoryEnum;
import kz.anilenses.webcommons.data.PageableInput;
import org.springframework.data.domain.Page;

public interface ProductService<T, V> {

    void upsert(T request);

    Page<V> findAllPageable(PageableInput pageable, List<SelectedField> fields);

    ProductCategoryEnum getCategory();

}
