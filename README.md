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
> To quickly understand the project, go the [CONTRIBUTING.md](https://github.com/phucnoob/Dictionary/blob/main/CONTRIBUTING.md)
### IntelliJ<br>
- Just clone this repo
  ```shell
  git clone https://github.com/phucnoob/Dictionary.git
  ```
- Open in intellij
  - Maven will sync automatic and required JDK11 but in case not:
  File->Project Structure -> Project -> set SDK to 11 (or above)
  - Open DictionaryApplication class and run the main function.
### Maven

- Make sure you install maven correctly, just follow [official website](https://maven.apache.org/install.html) 
- Set JAVA_HOME variable to the jdk11+ path
- Add $JAVA_HOME/bin to PATH.
- Clone the repo
  ```shell
  git clone https://github.com/phucnoob/Dictionary.git
  ```
- Go to Dictionary and run.
  ```shell
  cd Dictionary/
  mvn javafx:run
  ```
### Credits
This project use many library. See the detailed in [the pom file](https://github.com/phucnoob/Dictionary/blob/main/pom.xml)

The core ones is
- JavaFX - UI library
- Jackson - parse and stringify JSON
- SQLite JBDC - data store
- Apache Fluent - HTTP client (to use API)