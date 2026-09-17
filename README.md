# Magazine subscription manager

Desktop application built with JavaFX for managing magazine subscriptions, developed as a final project for the fourth year of high school. The graphical interface is entirely in Italian.

## Features

Using an eight-digit subscriber code, the application allows you to:

- register a new subscriber, with magazine name, personal details, email address and city;
- view the data of an already registered subscriber.

Validation prevents registering a code that already exists and viewing a code that does not exist. Each field in the registration form is checked individually: first and last name cannot contain digits, the email address must have a valid format with one of the allowed extensions (it, com, org, net, edu), and a city must be selected. Validation errors and the registration confirmation are shown through ControlsFX notifications.

## Interface

On launch, the home page appears, where the subscriber code is entered and a choice is made between registration and viewing.

![Home page](screenshots/homePage.png)

If a validation fails, the error is shown through a ControlsFX notification.

![Error notification](screenshots/notification.png)

From the home page, choosing to register a new subscriber with a code that does not exist yet opens the registration form.

![Registration page](screenshots/registrationPage.png)

Choosing to view an already registered code shows the subscriber's data in read-only fields.

![View page](screenshots/viewPage.png)

## Technologies

- Java 17
- JavaFX, for the graphical interface and scene navigation through FXML
- SQLite, through the org.xerial:sqlite-jdbc driver, for persisting subscriber data
- ControlsFX, for popup notifications
- Maven, for dependency management and the build

## Project structure

```
src/main/java/
├── module-info.java
└── com/example/gestioneabbonati/
    ├── GestioneAbbonatiApplication.java
    ├── HomeController.java
    ├── RegisterController.java
    ├── ViewController.java
    ├── Subscriber.java
    └── common/
        ├── DatabaseManager.java
        └── Methods.java

src/main/resources/com/example/gestioneabbonati/
├── GUIHome.fxml
├── GUIRegister.fxml
├── GUIView.fxml
└── styles/style.css

database/
├── abbonati.db
└── schema.sql
```

`DatabaseManager` handles the SQLite connection and the read and insert operations on the subscribers table. The database file is created automatically on first launch, in the `database` folder of the working directory.

## Requirements

- JDK 17 or higher
- Maven

## Installation and startup

1. Clone the repository:
   ```
   git clone https://github.com/angiro22/Applicazione-gestione-abbonati-riviste
   ```
2. Open the project with IntelliJ IDEA or Eclipse: Maven downloads and adds the dependencies automatically, ControlsFX included.
3. Run `GestioneAbbonatiApplication.java` from the IDE, or from a terminal:
   ```
   mvn javafx:run
   ```

## Notes

Project assigned by the computer science teacher as the final work of the fourth year.