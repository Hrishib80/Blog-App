# Blog App

A modern blog application built with Java backend and JavaScript frontend technologies.

🌐 **Live Website:** [https://mind-a-thought-blogapp.netlify.app/](https://mind-a-thought-blogapp.netlify.app/)

---

## 📋 Table of Contents

- [About](#about)
- [Tech Stack](#tech-stack)
- [Technologies & Tools](#technologies--tools)
- [Language Composition](#language-composition)
- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Contributing](#contributing)

---

## About

This is a full-stack blog application that allows users to create, read, update, and delete blog posts. The application combines the robustness of Java backend with modern frontend technologies to provide a seamless user experience.

---

## Tech Stack

### Backend
- **Java** (69.5%) - Core backend logic and server-side operations

### Frontend
- **JavaScript** (21.7%) - Interactive client-side functionality
- **HTML** (0.5%) - Markup and structure
- **CSS** (7%) - Styling and responsive design

### DevOps & Deployment
- **Docker** (1.3%) - Containerization for consistent deployment environments
- **Netlify** - Frontend hosting and deployment

---

## Technologies & Tools

### Backend Technologies
- Java Spring Framework (or similar Java web framework)
- RESTful APIs
- Database integration

### Frontend Technologies
- JavaScript (Vanilla or Framework-based)
- HTML5
- CSS3 for styling and responsive layouts

### Build & Deployment Tools
- Docker - For containerizing the application
- Netlify - For continuous deployment and hosting of the frontend
- Version Control - Git & GitHub

### Development Tools
- IDE/Code Editor (IntelliJ IDEA, VS Code, etc.)
- Package Managers
- Build Tools

---

## Language Composition

| Language | Percentage |
|----------|-----------|
| Java | 69.5% |
| JavaScript | 21.7% |
| CSS | 7.0% |
| Dockerfile | 1.3% |
| HTML | 0.5% |

---

## Features

- ✍️ Create new blog posts
- 📖 Read and browse blog posts
- ✏️ Edit existing blog posts
- 🗑️ Delete blog posts
- 📱 Responsive design
- 🎨 Clean and modern UI
- 🐳 Containerized deployment

---

## Installation

### Prerequisites
- Java (JDK 8 or higher)
- Node.js (for frontend dependencies if applicable)
- Docker (optional, for containerized setup)
- Git

### Backend Setup
```bash
# Clone the repository
git clone https://github.com/Hrishib80/Blog-App.git
cd Blog-App

# Build the Java application
mvn clean install

# Run the application
mvn spring-boot:run
```

### Frontend Setup
```bash
# Install dependencies (if using npm/yarn)
npm install

# Run the development server
npm start
```

### Docker Setup
```bash
# Build the Docker image
docker build -t blog-app .

# Run the container
docker run -p 8080:8080 blog-app
```

---

## Usage

1. Navigate to [https://mind-a-thought-blogapp.netlify.app/](https://mind-a-thought-blogapp.netlify.app/)
2. Browse existing blog posts
3. Create a new post by clicking "New Post"
4. Edit or delete posts as needed
5. All changes are synced with the backend

---

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

### Steps to Contribute
1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## License

This project is open source and available under the MIT License.

---

## Contact

For more information or questions, feel free to reach out or create an issue in the repository.

**Live Demo:** [mind-a-thought-blogapp.netlify.app](https://mind-a-thought-blogapp.netlify.app/)
