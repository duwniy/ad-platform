package com.adplatform.analytics.controller;

import com.adplatform.analytics.dto.CampaignStatsResponse;
import com.adplatform.analytics.dto.DashboardSummaryResponse;
import com.adplatform.analytics.service.AnalyticsService;
import com.adplatform.shared.dto.ApiResponse;
import com.adplatform.user.entity.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Analytics")
public class AnalyticsController {
    private final AnalyticsService analyticsService;

    @PostMapping("/banners/{bannerId}/view")
    public ResponseEntity<Void> recordView(
            @PathVariable Long bannerId,
            @RequestParam Long placementId) {
        analyticsService.recordView(bannerId, placementId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/banners/{bannerId}/click")
    public ResponseEntity<Void> recordClick(
            @PathVariable Long bannerId,
            @RequestParam Long placementId) {
        analyticsService.recordClick(bannerId, placementId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/campaigns/{campaignId}/stats")
    public ResponseEntity<ApiResponse<CampaignStatsResponse>> getCampaignStats(
            @PathVariable Long campaignId,
            @RequestParam(defaultValue = "7") int days) {
        return ResponseEntity.ok(ApiResponse.ok(analyticsService.getCampaignStats(campaignId, days)));
    }

    @GetMapping("/dashboard/summary")
    public ResponseEntity<ApiResponse<DashboardSummaryResponse>> getDashboardSummary(
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(ApiResponse.ok(analyticsService.getDashboardSummary(user.getId())));
    }
}
