package kz.anilenses.productservice.lenses.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import kz.anilenses.webcommons.data.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "lens_price_history")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LensPriceHistoryEntity extends AuditEntity {

    @Column(name = "price", nullable = false)
    @Comment("Цена линз")
    private BigDecimal price;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lens_id", nullable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Comment("Ссылка на линзу")
    private LensEntity lens;

}
