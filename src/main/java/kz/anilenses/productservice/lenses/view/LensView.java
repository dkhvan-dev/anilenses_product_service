package kz.anilenses.productservice.lenses.view;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import java.math.BigDecimal;
import kz.anilenses.productservice.lenses.entity.LensEntity;

@EntityView(LensEntity.class)
public interface LensView {

    @IdMapping
    Long getId();

    String getModel();
    String getBrand();
    String getDescription();
    String getColor();
    BigDecimal getMinOpticalPower();
    BigDecimal getMaxOpticalPower();
    BigDecimal getOpticalPowerStep();
    BigDecimal getDiameter();
    BigDecimal getCurvatureRadius();
    Long getTotalQuantity();
    Long getSalesQuantity();
    Boolean getIsAvailable();

}
