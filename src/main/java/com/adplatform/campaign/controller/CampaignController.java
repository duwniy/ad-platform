package com.adplatform.campaign.controller;

import com.adplatform.campaign.dto.CampaignRequest;
import com.adplatform.campaign.dto.CampaignResponse;
import com.adplatform.campaign.entity.CampaignStatus;
import com.adplatform.campaign.service.CampaignService;
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
@RequestMapping("/api/campaigns")
@RequiredArgsConstructor
@Tag(name = "Campaigns")
public class CampaignController {
    private final CampaignService campaignService;

    @PostMapping
    @PreAuthorize("hasRole('ADVERTISER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CampaignResponse>> create(
            @Valid @RequestBody CampaignRequest request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(ApiResponse.ok(campaignService.create(request, user.getId())));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CampaignResponse>>> getMyCampaigns(
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(ApiResponse.ok(campaignService.getByUser(user.getId())));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<CampaignResponse>> updateStatus(
            @PathVariable Long id,
            @RequestParam CampaignStatus status,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(ApiResponse.ok(campaignService.updateStatus(id, status, user.getId())));
    }
}
