package kz.anilenses.productservice.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import io.swagger.v3.oas.annotations.media.Schema;
import kz.anilenses.productservice.lenses.dto.LensUpsert;
import kz.anilenses.productservice.types.ProductCategoryEnum;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "Базовый класс для сохранения товара")
@JsonTypeInfo(use = Id.NAME, property = "category", visible = true)
@JsonSubTypes(
    value = @JsonSubTypes.Type(value = LensUpsert.class, name = "LENSES")
)
@Getter
@Setter
public abstract class ProductUpsert {

    @Schema(description = "Категория товара")
    private ProductCategoryEnum category;

    @Schema(description = "Опубликовать товар?")
    private Boolean toPublish;

}
