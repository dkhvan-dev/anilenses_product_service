package kz.anilenses.productservice.lenses.service;

import graphql.schema.SelectedField;
import java.math.BigDecimal;
import java.util.List;
import kz.anilenses.productservice.types.PageableInput;
import kz.anilenses.productservice.types.ProductCategoryEnum;
import kz.anilenses.productservice.types.ProductInterface;
import kz.anilenses.exceptionhandler.rest.exception.BadRequestException;
import kz.anilenses.productservice.lenses.dto.LensUpsert;
import kz.anilenses.productservice.lenses.entity.LensEntity;
import kz.anilenses.productservice.lenses.entity.LensEntity_;
import kz.anilenses.productservice.lenses.entity.LensPriceHistoryEntity;
import kz.anilenses.productservice.lenses.mapper.LensMapper;
import kz.anilenses.productservice.lenses.repository.LensRepository;
import kz.anilenses.productservice.service.ProductService;
import kz.anilenses.webcommons.data.AuditEntity_;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static kz.anilenses.webcommons.data.CommonSpecification.attributeEquals;
import static kz.anilenses.webcommons.data.CommonSpecification.attributeNotEquals;

@Slf4j
@Service
@RequiredArgsConstructor
public class LensServiceImpl implements ProductService<LensUpsert, ProductInterface> {

    private final LensRepository repository;

    @Transactional
    @Override
    public void upsert(LensUpsert request) {
        validateUpsert(request);
        var lensEntity = new LensEntity();

        if (request.getId() != null) {
            lensEntity = getEntityById(request.getId());
        }

        lensEntity = LensMapper.INSTANCE.toEntity(lensEntity, request);
        lensEntity = repository.save(lensEntity);

        if (request.getId() == null || !lensEntity.getActualPrice().getPrice().equals(request.getPrice())) {
            var newPrice = new LensPriceHistoryEntity();
            newPrice.setPrice(request.getPrice());
            newPrice.setLens(lensEntity);
            lensEntity.setActualPrice(newPrice);
        }
    }

    private void validateUpsert(LensUpsert request) {
        Specification<LensEntity> where = attributeEquals(LensEntity_.MODEL, request.getModel());
        where = where.and(attributeEquals(LensEntity_.BRAND, request.getBrand()));
        where = where.and(attributeEquals(LensEntity_.COLOR, request.getColor()));
        where = where.and(attributeEquals(LensEntity_.COLOR, request.getColor()));
        where = where.and(attributeEquals(LensEntity_.MIN_OPTICAL_POWER, request.getMinOpticalPower()));
        where = where.and(attributeEquals(LensEntity_.MAX_OPTICAL_POWER, request.getMaxOpticalPower()));
        where = where.and(attributeEquals(LensEntity_.DIAMETER, request.getDiameter()));
        where = where.and(attributeEquals(LensEntity_.CURVATURE_RADIUS, request.getCurvatureRadius()));

        if (request.getId() != null) {
            where = where.and(attributeNotEquals(AuditEntity_.ID, request.getId()));
        }

        if (repository.exists(where)) {
            log.error("Lens already exists by parameters: {}", request);
            throw new BadRequestException("Lens already exists");
        }
    }

    public LensEntity getEntityById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Lens not found by ID: " + id));
    }

    @Override
    public ProductInterface findAllPageable(PageableInput pageable, List<SelectedField> fields) {
        return null;
    }

    @Override
    public ProductCategoryEnum getCategory() {
        return ProductCategoryEnum.LENSES;
    }

}
