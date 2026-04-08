package com.adplatform.campaign.service;

import com.adplatform.campaign.dto.CampaignRequest;
import com.adplatform.campaign.dto.CampaignResponse;
import com.adplatform.campaign.entity.Campaign;
import com.adplatform.campaign.entity.CampaignStatus;
import com.adplatform.campaign.repository.CampaignRepository;
import com.adplatform.shared.exception.ResourceNotFoundException;
import com.adplatform.shared.exception.AccessDeniedException;
import com.adplatform.user.entity.User;
import com.adplatform.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CampaignService {
    private final CampaignRepository campaignRepository;
    private final UserRepository userRepository;

    public CampaignResponse create(CampaignRequest request, Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Campaign campaign = Campaign.builder()
            .user(user)
            .name(request.getName())
            .description(request.getDescription())
            .budget(request.getBudget())
            .status(CampaignStatus.DRAFT)
            .startDate(request.getStartDate())
            .endDate(request.getEndDate())
            .build();
        return mapToResponse(campaignRepository.save(campaign));
    }

    @Transactional(readOnly = true)
    public List<CampaignResponse> getByUser(Long userId) {
        return campaignRepository.findByUserIdOrderByCreatedAtDesc(userId)
            .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public CampaignResponse updateStatus(Long id, CampaignStatus status, Long userId) {
        Campaign campaign = getOwnedCampaign(id, userId);
        campaign.setStatus(status);
        return mapToResponse(campaignRepository.save(campaign));
    }

    private Campaign getOwnedCampaign(Long id, Long userId) {
        Campaign campaign = campaignRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Campaign not found"));
        if (!campaign.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("Not your campaign");
        }
        return campaign;
    }

    private CampaignResponse mapToResponse(Campaign c) {
        return CampaignResponse.builder()
            .id(c.getId())
            .name(c.getName())
            .description(c.getDescription())
            .budget(c.getBudget())
            .spent(c.getSpent())
            .status(c.getStatus().name())
            .startDate(c.getStartDate())
            .endDate(c.getEndDate())
            .createdAt(c.getCreatedAt())
            .bannerCount(c.getBanners().size())
            .build();
    }
}
