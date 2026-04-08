package com.adplatform.campaign.entity;

import com.adplatform.shared.entity.BaseEntity;
import com.adplatform.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "campaigns")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Campaign extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private BigDecimal budget;

    @Builder.Default
    private BigDecimal spent = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private CampaignStatus status = CampaignStatus.DRAFT;

    private LocalDate startDate;
    private LocalDate endDate;

    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Banner> banners = new ArrayList<>();
}
