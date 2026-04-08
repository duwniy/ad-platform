package com.adplatform.placement.dto;

import com.adplatform.placement.entity.PlacementType;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class PlacementResponse {
    private Long id;
    private String name;
    private String description;
    private String location;
    private String placementType;
    private boolean active;
}
