package com.adplatform.placement.controller;

import com.adplatform.campaign.dto.BannerResponse;
import com.adplatform.placement.dto.PlacementRequest;
import com.adplatform.placement.dto.PlacementResponse;
import com.adplatform.placement.service.PlacementService;
import com.adplatform.shared.dto.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/placements")
@RequiredArgsConstructor
@Tag(name = "Placements")
public class PlacementController {
    private final PlacementService placementService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<PlacementResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.ok(placementService.getAll()));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<PlacementResponse>> create(
            @Valid @RequestBody PlacementRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(placementService.create(request)));
    }

    @GetMapping("/{id}/serve")
    public ResponseEntity<ApiResponse<BannerResponse>> serveBanner(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(placementService.serveBanner(id)));
    }
}
