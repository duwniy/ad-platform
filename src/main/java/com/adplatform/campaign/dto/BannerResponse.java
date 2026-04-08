package com.adplatform.campaign.dto;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class BannerResponse {
    private Long id;
    private Long campaignId;
    private String title;
    private String imageUrl;
    private String clickUrl;
    private String bannerType;
    private boolean active;
    private Long totalViews;
    private Long totalClicks;
}
