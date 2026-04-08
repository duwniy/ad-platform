package com.adplatform.analytics.repository;

import com.adplatform.analytics.entity.BannerStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BannerStatRepository extends JpaRepository<BannerStat, Long> {
    Optional<BannerStat> findByBannerIdAndPlacementIdAndStatDate(
        Long bannerId, Long placementId, LocalDate statDate);

    List<BannerStat> findByBannerIdAndStatDateBetween(
        Long bannerId, LocalDate from, LocalDate to);

    @Query("""
        SELECT bs.statDate as date,
               SUM(bs.views) as totalViews,
               SUM(bs.clicks) as totalClicks
        FROM BannerStat bs
        WHERE bs.banner.campaign.id = :campaignId
        AND bs.statDate BETWEEN :from AND :to
        GROUP BY bs.statDate
        ORDER BY bs.statDate
        """)
    List<DailyStatProjection> findDailyStatsByCampaignId(
        @Param("campaignId") Long campaignId,
        @Param("from") LocalDate from,
        @Param("to") LocalDate to);

    @Query("""
        SELECT SUM(bs.views) as totalViews, SUM(bs.clicks) as totalClicks
        FROM BannerStat bs
        WHERE bs.banner.campaign.user.id = :userId
        """)
    SummaryStatProjection findSummaryByUserId(@Param("userId") Long userId);
}
