package com.adplatform.placement.repository;

import com.adplatform.placement.entity.Placement;
import com.adplatform.placement.entity.PlacementType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlacementRepository extends JpaRepository<Placement, Long> {
    List<Placement> findByActiveTrue();
    List<Placement> findByPlacementType(PlacementType type);
}
