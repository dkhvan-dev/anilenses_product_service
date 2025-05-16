package kz.anilenses.productservice.lenses.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import kz.anilenses.productservice.annotations.GraphQlFieldAlias;
import kz.anilenses.webcommons.data.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "lenses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LensEntity extends AuditEntity {

    @Column(name = "model", nullable = false)
    @Comment("Модель линз")
    private String model;

    @Column(name = "brand", nullable = false)
    @Comment("Бренд линз")
    private String brand;

    @Column(name = "description")
    @Comment("Описание линз")
    private String description;

    @Column(name = "color", nullable = false)
    @Comment("Цвет линз")
    private String color;

    @Column(name = "min_optical_power", nullable = false)
    @Comment("Мин. оптическая сила линз")
    private BigDecimal minOpticalPower;

    @Column(name = "max_optical_power", nullable = false)
    @Comment("Макс. оптическая сила линз")
    private BigDecimal maxOpticalPower;

    @Column(name = "optical_power_step", nullable = false)
    @Comment("Шаг оптической силы")
    private BigDecimal opticalPowerStep;

    @Column(name = "diameter", nullable = false)
    @Comment("Диаметр")
    private BigDecimal diameter;

    @Column(name = "curvature_radius", nullable = false)
    @Comment("Радиус кривизны")
    private BigDecimal curvatureRadius;

    @Column(name = "total_quantity", nullable = false)
    @Comment("Общее количество")
    private Long totalQuantity;

    @Column(name = "sales_quantity", nullable = false)
    @Comment("Проданное количество")
    private Long salesQuantity = 0L;

    @Column(name = "is_available", nullable = false)
    @Comment("Доступны?")
    private Boolean isAvailable = Boolean.TRUE;

    @GraphQlFieldAlias(value = "actualPrice", isParent = true)
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "actual_price_id", nullable = false, referencedColumnName = "id")
    private LensPriceHistoryEntity actualPrice;

}
