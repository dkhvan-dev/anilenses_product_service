package kz.anilenses.productservice.utils;

import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.EntityViewSetting;
import graphql.schema.SelectedField;
import jakarta.persistence.EntityManager;
import java.util.List;
import kz.anilenses.exceptionhandler.LocalizedException;
import kz.anilenses.webcommons.data.AuditEntity_;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static kz.anilenses.productservice.enums.ProductErrorCode.PRODUCT_NOT_FOUND;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductViewFetcher {

    private final EntityManager entityManager;
    private final EntityViewManager entityViewManager;
    private final CriteriaBuilderFactory criteriaBuilderFactory;

    public <T> T fetchData(Class<T> viewClass, Long productId, String... fields) {
        var setting = EntityViewSetting.create(viewClass);
        var entityClass = ViewHelper.getEntityClassFromView(viewClass);

        for (var field : fields) {
            setting.fetch(field);
        }

        var criteriaBuilder = criteriaBuilderFactory.create(entityManager, entityClass);
        var criteria = entityViewManager.applySetting(setting, criteriaBuilder);

        criteria.where(AuditEntity_.ID).eq(productId);
        return criteria.getSingleResult();
    }

    public <T> T fetchData(Class<T> viewClass, Long productId, List<SelectedField> fields) {
        var viewSetting = EntityViewSetting.create(viewClass);
        var entityClass = ViewHelper.getEntityClassFromView(viewClass);
        ViewHelper.fetchFields(viewSetting, fields, entityClass);

        if (viewSetting.getFetches().isEmpty()) {
            viewSetting.fetch(AuditEntity_.ID);
        }

        var criteriaBuilder = criteriaBuilderFactory.create(entityManager, entityClass);
        var criteria = entityViewManager.applySetting(viewSetting, criteriaBuilder);

        criteria.where(AuditEntity_.ID).eq(productId);
        var result = criteria.getResultList();

        if (result.isEmpty()) {
            log.error("{} with ID: {} not found", entityClass.getName(), productId);
            throw new LocalizedException(PRODUCT_NOT_FOUND);
        }

        return result.getFirst();
    }

}
