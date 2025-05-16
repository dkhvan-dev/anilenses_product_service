package kz.anilenses.productservice.lenses.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import kz.anilenses.productservice.dto.ProductUpsert;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "Создание/редактирование линз")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LensUpsert extends ProductUpsert {

    @Schema(description = "ID существующих линз")
    private Long id;

    @Size(max = 255)
    @NotBlank
    @Schema(description = "Модель линз")
    private String model;

    @Size(max = 255)
    @NotBlank
    @Schema(description = "Бренд линз")
    private String brand;

    @Schema(description = "Описание линз")
    private String description;

    @NotBlank
    @Schema(description = "Цвет линз")
    private String color;

    @Digits(integer = 2, fraction = 2)
    @NotNull
    @Schema(description = "Мин. оптическая сила линз")
    private BigDecimal minOpticalPower;

    @Digits(integer = 2, fraction = 2)
    @NotNull
    @Schema(description = "Макс. оптическая сила линз")
    private BigDecimal maxOpticalPower;

    @Digits(integer = 2, fraction = 2)
    @PositiveOrZero
    @NotNull
    @Schema(description = "Шаг оптической силы")
    private BigDecimal opticalPowerStep;

    @Digits(integer = 2, fraction = 2)
    @NotNull
    @Schema(description = "Диаметр")
    private BigDecimal diameter;

    @Digits(integer = 2, fraction = 2)
    @NotNull
    @Schema(description = "Радиус кривизны")
    private BigDecimal curvatureRadius;

    @PositiveOrZero
    @Digits(integer = 100, fraction = 2)
    @NotNull
    @Schema(description = "Цена линз")
    private BigDecimal price;

    @PositiveOrZero
    @NotNull
    @Schema(description = "Общее количество")
    private Long totalQuantity;

}
