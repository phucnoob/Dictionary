# Dictionary - Commandline
_This is the commandline version of dictionary._<br>
_You can find the source code or jar file in github's Release_
## Prerequisite
- Java 11
- Maven (build only)
## Installation
1. Go to release and download the jar file.
2. Cd to the downloaded jar directory and run
    ```bash
    java -jar ./Dictionary-1.0.0.jar
    ```
## Build Guide
### IntelliJ
1. Clone the repository _(Since this is private repo, you may need github account)_
    ```bash
   git clone -b commandline https://github.com/phucnoob/Dictionary
   ```
2. Open with IntelliJ and click build button.

### Maven
1. Clone the repository _(Since this is private repo, you may need github account)_
    ```bash
   git clone -b commandline https://github.com/phucnoob/Dictionary
   ```
2. Build jar with maven.
    ```
   cd Dictionary/
   mvn package
   ```
3. Run the jar
    ```bash
      java -jar target/Dictionary-1.0-SNAPSHOT.jar
    ```