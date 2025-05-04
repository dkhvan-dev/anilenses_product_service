package kz.anilenses.productservice.lenses.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
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

    @Column(name = "color")
    @Comment("Цвет линз")
    private String color;

    @Column(name = "min_optical_power")
    @Comment("Мин. оптическая сила линз")
    private BigDecimal minOpticalPower;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "actual_price_id", nullable = false, referencedColumnName = "id")
    private LensPriceHistoryEntity actualPrice;

}
