-- V3: Add missing audit columns to banner_placements table
ALTER TABLE banner_placements
    ADD COLUMN IF NOT EXISTS created_at TIMESTAMP,
    ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP;
