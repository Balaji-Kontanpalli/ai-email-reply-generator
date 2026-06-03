# AI Email Reply Generator

An AI-powered email reply generator that integrates directly into Gmail via a Chrome Extension, 
built with Spring Boot, Google Gemini 2.5 Flash API, React, and Docker.

---

## Demo

### Web Application
![Web App Demo](screenshots/webapp.png)

### Gmail Chrome Extension
![Chrome Extension Demo](screenshots/extension.png)

---

## Architecture
# AI Email Reply Generator

An AI-powered email reply generator that integrates directly into Gmail via a Chrome Extension, 
built with Spring Boot, Google Gemini 2.5 Flash API, React, and Docker.

---

## Demo

### Web Application
![Web App Demo](screenshots/webapp.png)

### Gmail Chrome Extension
![Chrome Extension Demo](screenshots/extension.png)

---

## Architecture
User (Gmail) → Chrome Extension → Spring Boot REST API → Google Gemini 2.5 Flash API
↑
React Web App

---

## Tech Stack

| Layer | Technology |
|---|---|
| Backend | Java 21, Spring Boot 3.x, WebClient (WebFlux) |
| AI Integration | Google Gemini 2.5 Flash API |
| Frontend | React, Vite, Material UI, Axios |
| Browser Extension | Chrome Extension (Manifest V3), MutationObserver |
| Containerization | Docker, Docker Compose, Nginx |
| Build Tool | Maven |
| Version Control | Git, GitHub |

---

## Features

- Generate AI-powered email replies with selectable tone (Professional / Casual / Friendly)
- Chrome Extension injects seamlessly into Gmail's compose window
- Copy generated reply to clipboard with one click
- Fully containerized with Docker — runs on any machine
- API key managed via environment variables — never hardcoded

---

## Project Structure
ai-email-reply-generator/
├── email-writer-sb/          # Spring Boot Backend
│   ├── src/
│   │   └── main/java/com/email/writer/app/
│   │       ├── EmailGeneratorController.java
│   │       ├── EmailGeneratorService.java
│   │       └── EmailRequest.java
│   ├── Dockerfile
│   └── pom.xml
│
├── email-writer-react/       # React Frontend
│   ├── src/
│   │   └── App.jsx
│   ├── Dockerfile
│   └── package.json
│
├── email-writer-ext/         # Chrome Extension
│   ├── content.js
│   ├── content.css
│   └── manifest.json
│
├── docker-compose.yml
├── .env.example
└── README.md

---

## Getting Started

### Prerequisites

- Docker Desktop installed
- Google Gemini API key → [Get it here](https://aistudio.google.com/app/apikey)
- Google Chrome browser

---

### Option 1 — Run with Docker Hub (Recommended)

No need to clone the repo. Just create these two files locally:

**1. Create `.env` file:**
GEMINI_API_URL=https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent
GEMINI_API_KEY=your_gemini_api_key_here

**2. Create `docker-compose.yml` file:**
```yaml
services:

  backend:
    image: balajikontanpalli/email-writer-backend:latest
    container_name: email-writer-backend
    ports:
      - "8080:8080"
    environment:
      - GEMINI_API_URL=${GEMINI_API_URL}
      - GEMINI_API_KEY=${GEMINI_API_KEY}
    networks:
      - email-writer-network

  frontend:
    image: balajikontanpalli/email-writer-frontend:latest
    container_name: email-writer-frontend
    ports:
      - "80:80"
    depends_on:
      - backend
    networks:
      - email-writer-network

networks:
  email-writer-network:
    driver: bridge
```

**3. Run:**
```bash
docker-compose up
```

**4. Open browser:** `http://localhost`

---

### Option 2 — Run from Source

**1. Clone the repository:**
```bash
git clone https://github.com/Balaji-Kontanpalli/ai-email-reply-generator.git
cd ai-email-reply-generator
```

**2. Create `.env` file in project root:**
GEMINI_API_URL=https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent
GEMINI_API_KEY=your_gemini_api_key_here

**3. Build and run:**
```bash
docker-compose up --build
```

**4. Open browser:** `http://localhost`

---

### Option 3 — Run Backend without Docker (IntelliJ)

**1. Set environment variables in IntelliJ:**

Run → Edit Configurations → Environment Variables:
GEMINI_API_URL=https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent
GEMINI_API_KEY=your_gemini_api_key_here

**2. Run the Spring Boot application**

API will be available at: `http://localhost:8080`

---

## API Reference

### Generate Email Reply
POST /api/email/generate

**Request Body:**
```json
{
  "emailContent": "Hi, hope you are doing well. Can we schedule a meeting?",
  "tone": "professional"
}
```

**Response:**
Thank you for reaching out. I would be happy to schedule a meeting.
Please share your availability and I will confirm a suitable time.

**Tone options:** `professional` | `casual` | `friendly`

---

## Chrome Extension Setup

1. Open Chrome → go to `chrome://extensions`
2. Enable **Developer Mode** (top right toggle)
3. Click **Load Unpacked**
4. Select the `email-writer-ext/` folder
5. Open Gmail → open any email → click **Reply**
6. Click the **AI Reply** button that appears in the compose toolbar
7. AI-generated reply will be inserted automatically

---

## Docker Hub

Images are publicly available on Docker Hub:

- Backend → `balajikontanpalli/email-writer-backend:latest`
- Frontend → `balajikontanpalli/email-writer-frontend:latest`

```bash
docker pull balajikontanpalli/email-writer-backend:latest
docker pull balajikontanpalli/email-writer-frontend:latest
```

---

## Environment Variables

| Variable | Description | Example |
|---|---|---|
| `GEMINI_API_URL` | Google Gemini API endpoint | `https://generativelanguage.googleapis.com/...` |
| `GEMINI_API_KEY` | Your Gemini API key | `AIza...` |

> Never commit your `.env` file to GitHub. It is listed in `.gitignore`.

---

## How It Works

### Backend Flow
1. Chrome Extension or React app sends `POST /api/email/generate` with email content and tone
2. `EmailGeneratorService` builds a context-aware prompt using the email content and tone
3. Spring Boot sends the prompt to Google Gemini 2.5 Flash API via `WebClient`
4. Gemini response is parsed using Jackson `JsonNode` — extracting `candidates[0].content.parts[0].text`
5. Clean reply string is returned to the client

### Chrome Extension Flow
1. `MutationObserver` watches Gmail's DOM for compose/reply window appearing
2. On detection, injects a styled **AI Reply** button matching Gmail's native UI
3. On button click, fetches email content from the DOM and calls Spring Boot API
4. AI-generated reply is inserted into the compose box via `document.execCommand('insertText')`

---

## Key Technical Decisions

| Decision | Reason |
|---|---|
| WebClient over RestTemplate | Non-blocking, reactive, and future-proof (RestTemplate is deprecated) |
| Multi-stage Docker builds | Smaller final image — build tools not included in runtime image |
| MutationObserver in extension | Gmail uses dynamic navigation with no full page reload |
| Environment variables for API key | Security best practice — secrets never hardcoded in source |
| Jackson JsonNode for response parsing | Flexible tree navigation for deeply nested Gemini API response |

---

## Author

**Balaji Kontanpalli**
- GitHub → [Balaji-Kontanpalli](https://github.com/Balaji-Kontanpalli)
- LinkedIn → [https://www.linkedin.com/in/balaji-kontanpalli/]
- Email → balajikontanpallimail@gmail.com

---



