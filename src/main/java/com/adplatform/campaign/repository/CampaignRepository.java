package com.adplatform.campaign.repository;

import com.adplatform.campaign.entity.Campaign;
import com.adplatform.campaign.entity.CampaignStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    List<Campaign> findByUserId(Long userId);
    List<Campaign> findByStatus(CampaignStatus status);
    List<Campaign> findByUserIdAndStatus(Long userId, CampaignStatus status);

    @Query("SELECT c FROM Campaign c WHERE c.user.id = :userId ORDER BY c.createdAt DESC")
    List<Campaign> findByUserIdOrderByCreatedAtDesc(@Param("userId") Long userId);
}
