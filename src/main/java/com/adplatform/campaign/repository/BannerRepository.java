package com.adplatform.campaign.repository;

import com.adplatform.campaign.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BannerRepository extends JpaRepository<Banner, Long> {

    List<Banner> findByCampaignId(Long campaignId);

    List<Banner> findByCampaignIdAndActiveTrue(Long campaignId);

    @Query("SELECT b FROM Banner b WHERE b.campaign.user.id = :userId AND b.active = true ORDER BY b.createdAt DESC")
    List<Banner> findByUserId(@Param("userId") Long userId);

    @Query("""
        SELECT b FROM Banner b
        JOIN b.campaign c
        JOIN BannerPlacement bp ON bp.banner = b
        WHERE bp.placement.id = :placementId
        AND b.active = true
        AND c.status = 'ACTIVE'
        """)
    List<Banner> findActiveBannersForPlacement(@Param("placementId") Long placementId);
}
