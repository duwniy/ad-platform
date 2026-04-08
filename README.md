# Ad Platform

A full-stack advertising platform designed to allow advertisers to manage campaigns and banners, and admins to manage deployment placements.

## Tech Stack
- **Backend:** Java 17, Spring Boot 3, PostgreSQL, Flyway, JWT Auth, Swagger
- **Frontend:** React 18 + Vite, TailwindCSS, Zustand, React-Router, Recharts

## Local Development (Docker Compose)
1. Run `docker-compose up -d --build` from the root directory.
2. The UI will be available at `http://localhost:3000`.
3. The Backend API and Swagger Documentation will be available at `http://localhost:8080/swagger-ui/index.html` (or `http://localhost:8080/v3/api-docs`).

## Initial Seed Data
The database is pre-seeded with sample users, campaigns, banners, placements, and mock analytical interactions via Flyway (`V2__seed_data.sql`).

* **Admin Role:** `admin@adplatform.com` / `password123`
* **Advertiser Role:** `advertiser@adplatform.com` / `password123`

## Deployment
* **Backend:** Can be deployed to Render via the provided `render.yaml`.
* **Frontend:** Can be deployed to Vercel via the provided `vercel.json` (Ensure the root directory points to `frontend` or update `vercel.json` commands).

## Project Structure
* `/backend`: Spring Boot Maven application.
* `/frontend`: React JS Vite application.
