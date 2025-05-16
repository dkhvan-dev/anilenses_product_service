package kz.anilenses.productservice.lenses.mapper;

import kz.anilenses.productservice.configs.CommonMapperConfig;
import kz.anilenses.productservice.lenses.dto.LensUpsert;
import kz.anilenses.productservice.lenses.entity.LensEntity;
import kz.anilenses.productservice.lenses.view.LensView;
import kz.anilenses.productservice.types.Lens;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(
    builder = @Builder(disableBuilder = true),
    config = CommonMapperConfig.class
)
public interface LensMapper {

    LensMapper INSTANCE = Mappers.getMapper(LensMapper.class);

    LensEntity toEntity(@MappingTarget LensEntity entity, LensUpsert request);

    Lens toResponse(LensView entityView);

}
