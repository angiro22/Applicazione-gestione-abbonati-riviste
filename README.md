
# Applicazione gestione abbonati riviste con JavaFX

This is a final project given to my class by our Computer Sceince teacher for the fourth year of high school, I'm an italian student, so all the text of the grafic are written in my language.

This project consists in an application that allow the user, through a subscriber code, to access and view or insert the magazine that he's subscribed to or want to subscribe for, and some of his personal data.
___
## How to make it work

To make the application work first clone the project

```bash
  git clone https://github.com/angiro22/Applicazione-gestione-abbonati-riviste
```

Open the project with your IDE (I suggest IntelliJ IDEA or Eclipse)

Go to the module-info file

```path
src/main/java/module-info.java
```
and change

```java
requires java.desktop;
```

to

```java
requires javafx.graphics;
```

then, to enable the `controlsfx` library, that allow to show notifications, modify the Project Structure:
- in IntelliJ navigate to `File » Project Structure...` or press `Ctrl+Alt+Shift+S`, than in `Project Setting` choose `Libraries` and click on the `+` button on the top left, now choose the library path: `libs/controlsfx-11.2.0.jar` then hit the `Apply` button
- in Eclipse navigate to `Java » Build Path » Configure Build Path...` now go to the `Libraries` tab, hit `Add External JARs...` and choose the library path: `libs/controlsfx-11.2.0.jar`
