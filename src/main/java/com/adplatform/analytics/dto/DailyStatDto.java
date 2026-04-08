package com.adplatform.analytics.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data @Builder
public class DailyStatDto {
    private LocalDate date;
    private Long views;
    private Long clicks;
    private Double ctr;
}
