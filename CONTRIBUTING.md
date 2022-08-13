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

### Database Table
```sqlite
create table sqlite_master
(
    type     TEXT,
    name     TEXT,
    tbl_name TEXT,
    rootpage INT,
    sql      TEXT
);

create table words
(
    word_id   INTEGER
        primary key,
    target    TEXT not null
        unique,
    phonetics TEXT not null
);

create table definitions
(
    definition_id INTEGER
        primary key,
    explain       TEXT    not null,
    type          TEXT    not null,
    word_id       INTEGER not null
        references words
            on update cascade on delete cascade
);

create unique index idx_words_target
    on words (target);
```