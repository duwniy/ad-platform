package com.adplatform.placement.service;

import com.adplatform.campaign.dto.BannerResponse;
import com.adplatform.campaign.entity.Banner;
import com.adplatform.campaign.repository.BannerRepository;
import com.adplatform.placement.dto.PlacementRequest;
import com.adplatform.placement.dto.PlacementResponse;
import com.adplatform.placement.entity.Placement;
import com.adplatform.placement.repository.PlacementRepository;
import com.adplatform.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PlacementService {
    private final PlacementRepository placementRepository;
    private final BannerRepository bannerRepository;
    private final Random random = new Random();

    @Transactional(readOnly = true)
    public List<PlacementResponse> getAll() {
        return placementRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public PlacementResponse create(PlacementRequest request) {
        Placement placement = Placement.builder()
                .name(request.getName())
                .description(request.getDescription())
                .location(request.getLocation())
                .placementType(request.getPlacementType())
                .active(true)
                .build();
        return mapToResponse(placementRepository.save(placement));
    }

    @Transactional(readOnly = true)
    public BannerResponse serveBanner(Long placementId) {
        List<Banner> banners = bannerRepository.findActiveBannersForPlacement(placementId);
        if (banners.isEmpty()) {
            throw new ResourceNotFoundException("No active banners available for this placement");
        }
        
        Banner selectedBanner = banners.get(random.nextInt(banners.size()));
        
        return BannerResponse.builder()
                .id(selectedBanner.getId())
                .campaignId(selectedBanner.getCampaign().getId())
                .title(selectedBanner.getTitle())
                .imageUrl(selectedBanner.getImageUrl())
                .clickUrl(selectedBanner.getClickUrl())
                .bannerType(selectedBanner.getBannerType().name())
                .active(selectedBanner.isActive())
                .build();
    }

    private PlacementResponse mapToResponse(Placement p) {
        return PlacementResponse.builder()
                .id(p.getId())
                .name(p.getName())
                .description(p.getDescription())
                .location(p.getLocation())
                .placementType(p.getPlacementType().name())
                .active(p.isActive())
                .build();
    }
}
