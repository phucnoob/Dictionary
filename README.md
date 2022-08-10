## Dictionary App
*This project is assigment for author university course*

### It has two versions.

This is the GUI written in JavaFX.<br>
The commandline version in the [commandline branch](https://github.com/phucnoob/Dictionary/tree/commandline)

## Notice!! Important
I designed and dev the app in my laptop,
which has 1920x1080 resolution and 100% scaling.
I'm not sure if JavaFX has some hDPI handling so
if the window is too small (or big), please change your screen config to mine. 
## Feature
- CRUD operation on word
- Fancy UI
- Google translate API

## Prerequisite
- Java 11 Runtime (OpenJDK recommended)
- Maven (Optional)

## Build guide
### IntelliJ<br>
Just clone this repo and import in IntelliJ

### Maven
    // TODO

### Credits
This project use many library. See the detailed in [the pom file](https://github.com/phucnoob/Dictionary/blob/main/pom.xml)

The core ones is
- JavaFX - UI library
- Jackson - parse and stringify JSON
- SQLite JBDC - data store
- Apache Fluent - HTTP client (to use API)