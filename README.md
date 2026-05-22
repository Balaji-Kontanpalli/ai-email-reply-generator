# AI Email Reply Generator

An AI-powered email reply generator built with Spring Boot, React, and a Chrome Extension.

## Tech Stack
- **Backend**: Spring Boot, WebClient, Jackson, Lombok
- **AI**: Google Gemini API
- **Frontend**: React, Material UI, Axios
- **Extension**: Chrome Extension (Manifest V3), MutationObserver, Content Scripts

## Architecture
Spring Boot REST API ← consumed by → React Web App + Chrome Extension (Gmail)

## Features
- Generate AI-based email replies with selectable tone (professional, casual, friendly)
- Chrome Extension injects directly into Gmail's compose window
- API key managed via environment variables (never hardcoded)

## How to Run (Backend)
1. Set environment variables: `GEMINI_API_URL` and `GEMINI_API_KEY`
2. Run: `mvn spring-boot:run`
3. API available at: `http://localhost:8080/api/email/generate`

## How to Load Extension
1. Open Chrome → `chrome://extensions`
2. Enable Developer Mode
3. Click "Load Unpacked" → select `email-writer-ext/` folder