package com.adplatform.campaign.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BannerRequest {

    @NotNull(message = "Campaign ID is required")
    private Long campaignId;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Image URL is required")
    private String imageUrl;

    private String clickUrl;

    private String bannerType = "IMAGE";
}
