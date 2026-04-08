package com.adplatform.campaign.entity;

import com.adplatform.shared.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "banners")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Banner extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_id", nullable = false)
    private Campaign campaign;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 1000)
    private String imageUrl;

    @Column(length = 1000)
    private String clickUrl;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private BannerType bannerType = BannerType.IMAGE;

    private Integer width;
    private Integer height;

    @Builder.Default
    private boolean active = true;
}
