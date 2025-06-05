# Intelligent Vehicle Operations Management System

A full-stack project based on Vue 3 and Spring Boot, designed to manage users, vehicles, geofencing, alerts, and dashboard data in a smart fleet environment. This is a personal learning project and is not intended for commercial use.

### Frontend

- **HTML5**: text tags, path management, semantic structure, forms, tables, lists, images, videos, hyperlinks
- **CSS3**: import methods, selectors, specificity, color units, display models, box model, float and positioning, transitions, animations, shadows, center layout
- **JavaScript (ES6)**: variable/constants, data types, control structures, DOM/BOM manipulation, functions, operators
- **Vue 3**: MVVM structure, Vue CLI, project organization, core directives (v-model, v-for, v-if, etc.)
- **Element Plus UI Library**: layout, menu, container, card, table, button, icon, message, form components (input, radio, select, switch), dialog, datetime picker, breadcrumb, pagination, upload, tags
- **Frontend-Backend Communication**: axios, qs, CORS
- **Local Storage**: store user session with localStorage
- **Global Configuration**: base URL config, language settings, loading dictionary data, handling ResizeObserverError
- **Map Integration**: using Google Maps JavaScript API with drawing tools to implement electronic geofence creation (circle and rectangle)

### Backend

- **Spring Boot**: base framework setup
- **Spring + Spring MVC + MyBatis**: layered architecture
- **Architecture**: standard 3-tier (Controller → Service → Mapper)
- **Database**: MySQL
- **API Documentation**: Knife4j
- **Validation**: Spring Validation for data constraints
- **Exception Handling**: business-level and global exception handler
- **RESTful API Design**:
- **Unified API Response (JsonResult)**

## Screenshots

- [Login Page](./screenshots/login.png)
- [Application List](./screenshots/application-list.png)
- [Dictionary Management](./screenshots/dictionary-management.png)
- [Geofence Map](./screenshots/geofence-map.png)

## Project Structure

```
root
├── frontend/      # Vue 3 + Element Plus frontend project
├── backend/       # Spring Boot backend project
├── screenshots/   # UI preview images
└── README.md
```
---

## Setup 

> Start backend first (ensure MySQL is configured), then run frontend using `npm run serve`.

---

## Contact

If you have any questions or concerns about the content, feel free to reach out via GitHub Issues.
