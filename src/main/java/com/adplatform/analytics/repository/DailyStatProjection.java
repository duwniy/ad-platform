package com.adplatform.analytics.repository;

import java.time.LocalDate;

public interface DailyStatProjection {
    LocalDate getDate();
    Long getTotalViews();
    Long getTotalClicks();
}
