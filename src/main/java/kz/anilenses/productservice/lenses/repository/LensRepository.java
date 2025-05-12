package kz.anilenses.productservice.lenses.repository;

import kz.anilenses.productservice.lenses.entity.LensEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LensRepository extends JpaRepository<LensEntity, Long>, JpaSpecificationExecutor<LensEntity> {

}
