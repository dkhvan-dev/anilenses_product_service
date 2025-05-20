package kz.anilenses.productservice.fetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import kz.anilenses.productservice.DgsConstants.QUERY;
import kz.anilenses.productservice.service.ProductServiceFactory;
import kz.anilenses.productservice.types.ProductCategoryEnum;
import kz.anilenses.productservice.types.ProductInterface;
import kz.anilenses.webcommons.data.PageableInput;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

@DgsComponent
@RequiredArgsConstructor
public class ProductDataFetcher {

    private final ProductServiceFactory productServiceFactory;

    @DgsQuery(field = QUERY.Products)
    public Page<ProductInterface> getPageableProducts(DgsDataFetchingEnvironment dfe,
                                                      @InputArgument ProductCategoryEnum category,
                                                      @InputArgument PageableInput pageable) {

        var fields = dfe.getSelectionSet().getFields();
        return productServiceFactory.getService(category).findAllPageable(pageable, fields);
    }

    @DgsQuery(field = QUERY.Product)
    public ProductInterface getProductById(DgsDataFetchingEnvironment dfe,
                                           @InputArgument Long id,
                                           @InputArgument ProductCategoryEnum category) {
        
        var fields = dfe.getSelectionSet().getFields();
        return productServiceFactory.getService(category).findById(id, fields);
    }

}
