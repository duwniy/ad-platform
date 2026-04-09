package com.adplatform.campaign.controller;

import com.adplatform.campaign.dto.BannerRequest;
import com.adplatform.campaign.dto.BannerResponse;
import com.adplatform.campaign.service.BannerService;
import com.adplatform.shared.dto.ApiResponse;
import com.adplatform.user.entity.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/banners")
@RequiredArgsConstructor
@Tag(name = "Banners")
public class BannerController {

    private final BannerService bannerService;

    @PostMapping
    @PreAuthorize("hasRole('ADVERTISER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<BannerResponse>> create(
            @Valid @RequestBody BannerRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(bannerService.create(request)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BannerResponse>>> getMyBanners(
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(ApiResponse.ok(bannerService.getByUser(user.getId())));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BannerResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(bannerService.getById(id)));
    }

    @PatchMapping("/{id}/toggle")
    @PreAuthorize("hasRole('ADVERTISER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<BannerResponse>> toggleActive(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(bannerService.toggleActive(id)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADVERTISER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        bannerService.delete(id);
        return ResponseEntity.ok(ApiResponse.ok(null, "Banner deleted"));
    }
}
