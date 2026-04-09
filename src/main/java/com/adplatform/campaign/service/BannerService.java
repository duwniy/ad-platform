package com.adplatform.campaign.service;

import com.adplatform.campaign.dto.BannerRequest;
import com.adplatform.campaign.dto.BannerResponse;
import com.adplatform.campaign.entity.Banner;
import com.adplatform.campaign.entity.BannerType;
import com.adplatform.campaign.entity.Campaign;
import com.adplatform.campaign.repository.BannerRepository;
import com.adplatform.campaign.repository.CampaignRepository;
import com.adplatform.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BannerService {

    private final BannerRepository bannerRepository;
    private final CampaignRepository campaignRepository;

    public BannerResponse create(BannerRequest request) {
        Campaign campaign = campaignRepository.findById(request.getCampaignId())
            .orElseThrow(() -> new ResourceNotFoundException("Campaign not found"));
        Banner banner = Banner.builder()
            .campaign(campaign)
            .title(request.getTitle())
            .imageUrl(request.getImageUrl())
            .clickUrl(request.getClickUrl())
            .bannerType(BannerType.valueOf(request.getBannerType()))
            .active(true)
            .build();
        return mapToResponse(bannerRepository.save(banner));
    }

    @Transactional(readOnly = true)
    public List<BannerResponse> getByUser(Long userId) {
        // ИСПРАВЛЕНО: заменено на query поиск по userId
        return bannerRepository.findByUserId(userId)
            .stream()
            .map(this::mapToResponse)
            .toList();
    }

    @Transactional(readOnly = true)
    public BannerResponse getById(Long id) {
        return mapToResponse(bannerRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Banner not found")));
    }

    public BannerResponse toggleActive(Long id) {
        Banner banner = bannerRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Banner not found"));
        banner.setActive(!banner.isActive());
        return mapToResponse(bannerRepository.save(banner));
    }

    public void delete(Long id) {
        Banner banner = bannerRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Banner not found"));
        banner.setActive(false);
        bannerRepository.save(banner);
    }

    private BannerResponse mapToResponse(Banner b) {
        return BannerResponse.builder()
            .id(b.getId())
            .campaignId(b.getCampaign().getId())
            .title(b.getTitle())
            .imageUrl(b.getImageUrl())
            .clickUrl(b.getClickUrl())
            .bannerType(b.getBannerType().name())
            .active(b.isActive())
            .build();
    }
}
