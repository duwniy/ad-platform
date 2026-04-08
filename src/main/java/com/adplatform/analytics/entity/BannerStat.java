package com.adplatform.analytics.entity;

import com.adplatform.campaign.entity.Banner;
import com.adplatform.placement.entity.Placement;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "banner_stats")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BannerStat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "banner_id", nullable = false)
    private Banner banner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "placement_id", nullable = false)
    private Placement placement;

    @Builder.Default
    private Long views = 0L;

    @Builder.Default
    private Long clicks = 0L;

    @Builder.Default
    private LocalDate statDate = LocalDate.now();

    @Column(updatable = false)
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}
