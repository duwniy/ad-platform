package com.adplatform.analytics.service;

import com.adplatform.analytics.dto.CampaignStatsResponse;
import com.adplatform.analytics.dto.DailyStatDto;
import com.adplatform.analytics.dto.DashboardSummaryResponse;
import com.adplatform.analytics.entity.BannerStat;
import com.adplatform.analytics.repository.BannerStatRepository;
import com.adplatform.analytics.repository.DailyStatProjection;
import com.adplatform.analytics.repository.SummaryStatProjection;
import com.adplatform.campaign.entity.Banner;
import com.adplatform.campaign.repository.BannerRepository;
import com.adplatform.placement.entity.Placement;
import com.adplatform.placement.repository.PlacementRepository;
import com.adplatform.shared.exception.ResourceNotFoundException;
import com.adplatform.campaign.repository.CampaignRepository;
import com.adplatform.campaign.entity.CampaignStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional
public class AnalyticsService {
    private final BannerStatRepository bannerStatRepository;
    private final BannerRepository bannerRepository;
    private final PlacementRepository placementRepository;
    private final CampaignRepository campaignRepository;

    public void recordView(Long bannerId, Long placementId) {
        BannerStat stat = getOrCreateStat(bannerId, placementId);
        stat.setViews(stat.getViews() + 1);
        bannerStatRepository.save(stat);
    }

    public void recordClick(Long bannerId, Long placementId) {
        BannerStat stat = getOrCreateStat(bannerId, placementId);
        stat.setClicks(stat.getClicks() + 1);
        bannerStatRepository.save(stat);
    }

    private BannerStat getOrCreateStat(Long bannerId, Long placementId) {
        return bannerStatRepository
            .findByBannerIdAndPlacementIdAndStatDate(bannerId, placementId, LocalDate.now())
            .orElseGet(() -> {
                Banner banner = bannerRepository.findById(bannerId)
                    .orElseThrow(() -> new ResourceNotFoundException("Banner not found"));
                Placement placement = placementRepository.findById(placementId)
                    .orElseThrow(() -> new ResourceNotFoundException("Placement not found"));
                return BannerStat.builder()
                    .banner(banner)
                    .placement(placement)
                    .views(0L)
                    .clicks(0L)
                    .statDate(LocalDate.now())
                    .createdAt(LocalDateTime.now())
                    .build();
            });
    }

    @Transactional(readOnly = true)
    public CampaignStatsResponse getCampaignStats(Long campaignId, int days) {
        LocalDate from = LocalDate.now().minusDays(days);
        LocalDate to = LocalDate.now();
        List<DailyStatProjection> daily =
            bannerStatRepository.findDailyStatsByCampaignId(campaignId, from, to);
        long totalViews = daily.stream().mapToLong(DailyStatProjection::getTotalViews).sum();
        long totalClicks = daily.stream().mapToLong(DailyStatProjection::getTotalClicks).sum();
        double ctr = totalViews > 0 ? (double) totalClicks / totalViews * 100 : 0;
        return CampaignStatsResponse.builder()
            .campaignId(campaignId)
            .totalViews(totalViews)
            .totalClicks(totalClicks)
            .ctr(Math.round(ctr * 100.0) / 100.0)
            .dailyStats(daily.stream().map(d -> DailyStatDto.builder()
                .date(d.getDate())
                .views(d.getTotalViews())
                .clicks(d.getTotalClicks())
                .ctr(d.getTotalViews() > 0 ? Math.round((double) d.getTotalClicks() / d.getTotalViews() * 10000.0) / 100.0 : 0)
                .build()).collect(Collectors.toList()))
            .build();
    }
    
    @Transactional(readOnly = true)
    public DashboardSummaryResponse getDashboardSummary(Long userId) {
        SummaryStatProjection summary = bannerStatRepository.findSummaryByUserId(userId);
        long totalViews = summary != null && summary.getTotalViews() != null ? summary.getTotalViews() : 0L;
        long totalClicks = summary != null && summary.getTotalClicks() != null ? summary.getTotalClicks() : 0L;
        double averageCtr = totalViews > 0 ? (double) totalClicks / totalViews * 100 : 0;
        
        int activeCampaigns = campaignRepository.findByUserIdAndStatus(userId, CampaignStatus.ACTIVE).size();
        
        return DashboardSummaryResponse.builder()
            .totalViews(totalViews)
            .totalClicks(totalClicks)
            .averageCtr(Math.round(averageCtr * 100.0) / 100.0)
            .activeCampaigns(activeCampaigns)
            .totalBanners(0) // Simplified for dashboard summary
            .totalSpent(BigDecimal.ZERO) // Would aggregate spending in real app
            .last7DaysStats(List.of()) // Would return actual chart data per requirement
            .build();
    }
}
