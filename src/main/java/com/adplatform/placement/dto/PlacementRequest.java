package com.adplatform.placement.dto;

import com.adplatform.placement.entity.PlacementType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PlacementRequest {
    @NotBlank
    private String name;
    private String description;
    private String location;
    @NotNull
    private PlacementType placementType;
}
