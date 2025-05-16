package kz.anilenses.productservice.utils;

import com.blazebit.persistence.FullQueryBuilder;
import com.blazebit.persistence.PaginatedCriteriaBuilder;
import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.EntityViewSetting;
import graphql.schema.SelectedField;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kz.anilenses.productservice.annotations.GraphQlFieldAlias;
import kz.anilenses.webcommons.data.AuditEntity_;
import kz.anilenses.webcommons.data.PageableInput;
import kz.anilenses.webcommons.data.SortInput;
import kz.anilenses.webcommons.data.SortInput.SortDirection;
import kz.anilenses.webcommons.utils.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class ViewHelper {

    public static Class<?> getEntityClassFromView(Class<?> viewClass) {
        EntityView entityViewAnnotation = viewClass.getAnnotation(EntityView.class);

        if (entityViewAnnotation == null) {
            throw new IllegalArgumentException("Class " + viewClass.getName() + " not annotated @EntityView");
        }

        return entityViewAnnotation.value();
    }

    public static <T, Q extends FullQueryBuilder<T, Q>> void fetchFields(EntityViewSetting<T, Q> viewSetting,
                                                                         List<SelectedField> fields,
                                                                         Class<?> entityClass) {

        fields = fields.stream()
            .filter(field -> !field.getName().matches("__typename|content|totalElements"))
            .toList();

        for (SelectedField field : fields) {
            var fieldPath = field.getQualifiedName()
                .replaceFirst(".*?content/", "")
                .split("/");

            var fieldName = getFieldName(fieldPath, entityClass);
            if (fieldName != null && !List.of("content", "totalElements").contains(fieldName)) {
                viewSetting.fetch(fieldName);
            }
        }
    }

    private static String getFieldName(String[] fieldPath, Class<?> entityClass) {
        if (fieldPath == null || fieldPath.length == 0) {
            return null;
        }

        var fieldName = fieldPath[0];
        var annotatedFields = getAnnotatedFieldMappings(entityClass);
        var annotatedField = annotatedFields.get(fieldName);

        if (annotatedField != null) {
            fieldName = getAnnotatedFieldName(fieldName, fieldPath, annotatedField, entityClass);
        }

        return fieldName;
    }

    private static String getAnnotatedFieldName(String fieldName, String[] fieldPath, Field field, Class<?> entityClass) {
        var fieldAnnotation = field.getAnnotation(GraphQlFieldAlias.class);
        if (fieldAnnotation != null && fieldAnnotation.isParent()) {
            entityClass = getFieldClass(field);
        } else if (fieldAnnotation != null) {
            return fieldPath.length == 1 ? null : field.getName();
        }

        var childFieldName = getFieldName(Arrays.copyOfRange(fieldPath, 1, fieldPath.length), entityClass);
        if (StringUtils.isBlank(childFieldName)) {
            return null;
        }

        return String.join(".", fieldName, childFieldName);
    }

    private static Map<String, Field> getAnnotatedFieldMappings(Class<?> entityClass) {
        var fieldMappings = new HashMap<String, Field>();

        for (var field : entityClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(GraphQlFieldAlias.class)) {
                var alias = field.getAnnotation(GraphQlFieldAlias.class).value();
                fieldMappings.put(alias, field);
            }
        }

        return fieldMappings;
    }

    private static Class<?> getFieldClass(Field field) {
        if (Collection.class.isAssignableFrom(field.getType())) {
            if (field.getGenericType() instanceof ParameterizedType type) {
                return (Class<?>) type.getActualTypeArguments()[0];
            }
        }

        return field.getType();
    }

    public static void applySorting(PaginatedCriteriaBuilder<?> criteria, List<SortInput> sortInputs) {
        if (sortInputs == null || sortInputs.isEmpty()) {
            criteria.orderByAsc(AuditEntity_.ID);
            return;
        }

        for (SortInput sort : sortInputs) {
            String sortField = sort.getField();
            boolean isAscending = sort.getDirection() == SortDirection.ASC;

            if (isAscending) {
                criteria.orderByAsc(sortField);
            } else {
                criteria.orderByDesc(sortField);
            }
        }
    }

    public static <T> Page<T> mapPageResult(PageableInput pageRequest, PaginatedCriteriaBuilder<T> criteria) {
        var result = criteria.getResultList();

        var data = new ArrayList<T>();
        var totalElements = -1L;
        if (result != null) {
            data = new ArrayList<>(result);
            totalElements = result.getTotalSize();
        }

        return new PageImpl<>(data, PageHelper.mapPageRequest(pageRequest), totalElements);
    }

}
