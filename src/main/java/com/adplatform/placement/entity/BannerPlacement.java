package com.adplatform.placement.entity;

import com.adplatform.campaign.entity.Banner;
import com.adplatform.shared.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "banner_placements")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BannerPlacement extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "banner_id", nullable = false)
    private Banner banner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "placement_id", nullable = false)
    private Placement placement;

    @Builder.Default
    private boolean active = true;
}
