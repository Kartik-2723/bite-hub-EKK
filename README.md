<img width="1712" height="1008" alt="image" src="https://github.com/user-attachments/assets/fe6b7af6-914b-4fb9-9ebe-4cfe21aa84a6" />

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

The system follows a modular backend design:

- **Controller Layer** → Handles API requests
- **Service Layer** → Business logic
- **Repository Layer** → Elasticsearch interaction
- **Security Layer** → Keycloak integration

---

## 🧠 Domain Model

### Core Entities:

#### 🏪 Restaurant
- id, name, cuisineType
- contactInformation
- averageRating
- geoLocation (GeoPoint)
- has:
  - Address
  - OperatingHours
  - Reviews

#### 📍 Address
- streetNumber, streetName, unit
- city, state, postalCode, country

#### 🕒 OperatingHours
- TimeRange for each day (Mon–Sun)

#### ⏰ TimeRange
- openTime, closeTime

#### 📝 Review
- content, rating
- datePosted, lastEdited
- linked to:
  - Restaurant
  - User
  - Photos

#### 👤 User
- username, givenName, familyName

#### 📸 Photo
- url, uploadDate

---

## 🔗 Relationships

- Restaurant → has one Address & OperatingHours
- Restaurant → has many Reviews
- Review → created by one User
- Review → has multiple Photos

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
