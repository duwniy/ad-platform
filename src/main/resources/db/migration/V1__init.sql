-- V1__init.sql

CREATE TABLE users (
    id          BIGSERIAL PRIMARY KEY,
    email       VARCHAR(255) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    full_name   VARCHAR(255),
    role        VARCHAR(50)  NOT NULL DEFAULT 'ADVERTISER',
    active      BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE TABLE campaigns (
    id           BIGSERIAL PRIMARY KEY,
    user_id      BIGINT       NOT NULL REFERENCES users(id),
    name         VARCHAR(255) NOT NULL,
    description  TEXT,
    budget       NUMERIC(12,2),
    spent        NUMERIC(12,2) NOT NULL DEFAULT 0,
    status       VARCHAR(50)  NOT NULL DEFAULT 'DRAFT',
    start_date   DATE,
    end_date     DATE,
    created_at   TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at   TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE TABLE banners (
    id           BIGSERIAL PRIMARY KEY,
    campaign_id  BIGINT       NOT NULL REFERENCES campaigns(id),
    title        VARCHAR(255) NOT NULL,
    image_url    VARCHAR(1000) NOT NULL,
    click_url    VARCHAR(1000),
    banner_type  VARCHAR(50)  NOT NULL DEFAULT 'IMAGE',
    width        INT,
    height       INT,
    active       BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at   TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at   TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE TABLE placements (
    id           BIGSERIAL PRIMARY KEY,
    name         VARCHAR(255) NOT NULL,
    description  TEXT,
    location     VARCHAR(500),
    placement_type VARCHAR(50) NOT NULL DEFAULT 'SCREEN',
    active       BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at   TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at   TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE TABLE banner_placements (
    id            BIGSERIAL PRIMARY KEY,
    banner_id     BIGINT NOT NULL REFERENCES banners(id),
    placement_id  BIGINT NOT NULL REFERENCES placements(id),
    active        BOOLEAN NOT NULL DEFAULT TRUE,
    UNIQUE(banner_id, placement_id)
);

CREATE TABLE banner_stats (
    id            BIGSERIAL PRIMARY KEY,
    banner_id     BIGINT NOT NULL REFERENCES banners(id),
    placement_id  BIGINT NOT NULL REFERENCES placements(id),
    views         BIGINT NOT NULL DEFAULT 0,
    clicks        BIGINT NOT NULL DEFAULT 0,
    stat_date     DATE   NOT NULL DEFAULT CURRENT_DATE,
    created_at    TIMESTAMP NOT NULL DEFAULT NOW(),
    UNIQUE(banner_id, placement_id, stat_date)
);

CREATE TABLE targeting_rules (
    id           BIGSERIAL PRIMARY KEY,
    campaign_id  BIGINT       NOT NULL REFERENCES campaigns(id),
    rule_type    VARCHAR(50)  NOT NULL,
    rule_value   VARCHAR(255) NOT NULL,
    created_at   TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_campaigns_user_id ON campaigns(user_id);
CREATE INDEX idx_banners_campaign_id ON banners(campaign_id);
CREATE INDEX idx_banner_stats_banner_id ON banner_stats(banner_id);
CREATE INDEX idx_banner_stats_stat_date ON banner_stats(stat_date);
CREATE INDEX idx_targeting_rules_campaign_id ON targeting_rules(campaign_id);
