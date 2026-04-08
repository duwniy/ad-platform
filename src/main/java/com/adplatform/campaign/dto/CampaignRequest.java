package com.adplatform.campaign.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CampaignRequest {
    @NotBlank
    private String name;
    private String description;
    private BigDecimal budget;
    private LocalDate startDate;
    private LocalDate endDate;
}
