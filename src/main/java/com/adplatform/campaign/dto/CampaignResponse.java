package com.adplatform.campaign.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data @Builder
public class CampaignResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal budget;
    private BigDecimal spent;
    private String status;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime createdAt;
    private int bannerCount;
}
