-- V2__seed_data.sql

INSERT INTO users (email, password, full_name, role) VALUES
('admin@adplatform.com', '$2a$10$hash_here', 'Admin User', 'ADMIN'),
('advertiser@example.com', '$2a$10$hash_here', 'John Advertiser', 'ADVERTISER'),
('brand@example.com', '$2a$10$hash_here', 'Brand Manager', 'ADVERTISER');

INSERT INTO placements (name, description, location, placement_type) VALUES
('Экран ТЦ Мега — Вход', 'Большой LED экран у главного входа', 'ТЦ Мега, г. Ташкент', 'SCREEN'),
('Билборд Чиланзар', 'Билборд на пересечении улиц', 'Чиланзар, г. Ташкент', 'BILLBOARD'),
('Экран Аэропорт', 'Цифровой экран в зале ожидания', 'Аэропорт Ташкент', 'SCREEN'),
('Сайт новости.uz', 'Баннерная площадка новостного портала', 'online', 'WEBSITE');

INSERT INTO campaigns (user_id, name, description, budget, status, start_date, end_date) VALUES
(2, 'Летняя распродажа 2024', 'Кампания для летней акции', 50000.00, 'ACTIVE', '2024-06-01', '2024-08-31'),
(2, 'Новая коллекция', 'Продвижение новой коллекции товаров', 30000.00, 'ACTIVE', '2024-07-01', '2024-12-31'),
(3, 'Бренд-кампания', 'Повышение узнаваемости бренда', 100000.00, 'ACTIVE', '2024-01-01', '2024-12-31');

INSERT INTO banners (campaign_id, title, image_url, click_url, banner_type) VALUES
(1, 'Скидка 50%', 'https://picsum.photos/800/400?random=1', 'https://example.com/sale', 'IMAGE'),
(1, 'Летние товары', 'https://picsum.photos/800/400?random=2', 'https://example.com/summer', 'IMAGE'),
(2, 'Новая коллекция осень', 'https://picsum.photos/800/400?random=3', 'https://example.com/new', 'IMAGE'),
(3, 'Мы работаем для вас', 'https://picsum.photos/800/400?random=4', 'https://example.com/brand', 'IMAGE');

INSERT INTO banner_stats (banner_id, placement_id, views, clicks, stat_date) VALUES
(1, 1, 1520, 45, CURRENT_DATE - 6),
(1, 1, 1830, 62, CURRENT_DATE - 5),
(1, 1, 2100, 71, CURRENT_DATE - 4),
(1, 1, 1950, 58, CURRENT_DATE - 3),
(1, 1, 2250, 89, CURRENT_DATE - 2),
(1, 1, 2400, 95, CURRENT_DATE - 1),
(1, 1, 2600, 110, CURRENT_DATE),
(2, 2, 900, 30, CURRENT_DATE - 2),
(2, 2, 1100, 42, CURRENT_DATE - 1),
(2, 2, 1300, 55, CURRENT_DATE),
(3, 3, 500, 15, CURRENT_DATE - 1),
(3, 3, 650, 22, CURRENT_DATE),
(4, 4, 3000, 120, CURRENT_DATE - 1),
(4, 4, 3500, 145, CURRENT_DATE);
