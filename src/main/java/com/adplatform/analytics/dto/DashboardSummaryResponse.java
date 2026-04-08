package com.adplatform.analytics.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data @Builder
public class DashboardSummaryResponse {
    private Long totalViews;
    private Long totalClicks;
    private Double averageCtr;
    private int activeCampaigns;
    private int totalBanners;
    private BigDecimal totalSpent;
    private List<DailyStatDto> last7DaysStats;
}
