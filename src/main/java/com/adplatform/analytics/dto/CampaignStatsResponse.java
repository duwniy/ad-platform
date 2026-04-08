package com.adplatform.analytics.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data @Builder
public class CampaignStatsResponse {
    private Long campaignId;
    private String campaignName;
    private Long totalViews;
    private Long totalClicks;
    private Double ctr; 
    private BigDecimal spent;
    private List<DailyStatDto> dailyStats;
}
