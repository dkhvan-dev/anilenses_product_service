package kz.anilenses.productservice.lenses.view;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import kz.anilenses.productservice.lenses.entity.LensPriceHistoryEntity;

@EntityView(LensPriceHistoryEntity.class)
public interface LensPriceHistoryView {

    @IdMapping
    Long getId();

    OffsetDateTime getCreatedAt();
    BigDecimal getPrice();

}
