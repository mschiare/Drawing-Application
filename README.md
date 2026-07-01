# Drawing Application – Software Architecture & Design

## Overview

Drawing Application is a JavaFX desktop application.

The application provides an interactive canvas where users can create, edit and organize graphical objects. Its architecture is based on several GoF (Gang of Four) design patterns to ensure modularity, maintainability and extensibility.

---

## Features

### Drawing Tools

- Rectangle
- Ellipse
- Line
- Polygon
- Text

### Editing

- Select objects
- Move objects
- Resize objects
- Rotate objects
- Change fill color
- Change stroke color
- Change stroke width
- Edit text and font size
- Bring object to front
- Send object to back

### Clipboard

- Copy
- Cut
- Paste
- Delete selected objects

### Grid

- Enable/disable grid
- Custom number of rows
- Custom number of columns
- Automatic grid update when the canvas is resized

### View

- Zoom in
- Zoom out

### File Management

- Open project
- Save project

### History

- Undo support through the Command Pattern

---

## Design Patterns

The project implements several software design patterns, including:

- Factory Pattern
- Command Pattern
- State Pattern
- Observer Pattern
- Decorator Pattern

These patterns separate responsibilities and simplify future extensions.

---

## Technologies

- Java 21
- JavaFX 23
- Maven
- JUnit 5
- Mockito

---

The application follows an MVC-inspired architecture:

- **Model** – application data and business logic
- **View** – JavaFX interface (FXML)
- **Controller** – interaction management

---

## Requirements

- JDK 21 or newer
- Maven 3.9+
- JavaFX 23

---

## Installation

Clone the repository:

```bash
git clone https://github.com/<your-username>/<repository>.git
```

Enter the project directory:

```bash
cd Progetto_SAD_Gruppo14_AH
```

Compile:

```bash
mvn clean install
```

Run:

```bash
mvn javafx:run
```

---



