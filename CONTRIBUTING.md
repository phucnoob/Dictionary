### Project structure:
- api : Handle request googletrans api and text to speech api.
- commandline: commandline version (just ignore it)
- dao: Data Access Object - Handle SQL operation.
- services: use dao function to do the work UI request.
- controllers: Use services to update data and update ui.
- data: Word and Definition bean object.
- ui: Custom JavaFX widget
- utils: convenient function and storing constants
- DictionaryApplication: main class