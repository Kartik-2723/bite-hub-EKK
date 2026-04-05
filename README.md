
# 🍽️ Bite-EKK

A scalable **Restaurant Review Backend System** built using **Elasticsearch, Kibana, and Keycloak (EKK stack)**.  
Designed to handle high-performance search, secure authentication, and structured restaurant-review data.

---

## 🚀 Features

- 🔍 Advanced restaurant search using Elasticsearch
- ⭐ Restaurant & Review CRUD operations
- 👤 User-based review system
- 📊 Real-time analytics with Kibana
- 🔐 Secure authentication & authorization with Keycloak
- 📍 Geo-based restaurant queries (GeoPoint support)
- 🕒 Structured operating hours management

---

## 🏗️ Architecture Overview

---
<img width="1211" height="961" alt="image" src="https://github.com/user-attachments/assets/04d68a89-931c-4dfa-99f1-1c8e73bbde74" />
---

## 🧠 Domain Model
---
<img width="1712" height="1008" alt="image" src="https://github.com/user-attachments/assets/fe6b7af6-914b-4fb9-9ebe-4cfe21aa84a6" />
---

## 🛠️ Tech Stack

- **Backend:** Java, Spring Boot
- **Search Engine:** Elasticsearch
- **Visualization:** Kibana
- **Authentication:** Keycloak
- **Build Tool:** Maven

---

## 🔍 Elasticsearch Usage

- Full-text search on restaurant name & cuisine
- Geo-based queries using `geo_point`
- Aggregations for rating analytics

---

## 🔐 Security (Keycloak)

- OAuth2 / JWT-based authentication
- Role-based access control
- Secured endpoints for:
  - Creating reviews
  - Deleting resources

---

## 📊 Kibana

- Visualize:
  - Restaurant ratings distribution
  - User activity
  - Review trends

---

## ⚙️ How to Run

### 1. Clone Repository
```bash
git clone https://github.com/Kartik-2723/bite-hub-EKK.git
cd bite-hub-EKK
