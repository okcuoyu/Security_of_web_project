-- users tablosu
CREATE TABLE IF NOT EXISTS users (
                                     id INTEGER PRIMARY KEY AUTOINCREMENT,
                                     username TEXT NOT NULL UNIQUE,
                                     email TEXT NOT NULL UNIQUE,
                                     password TEXT NOT NULL,
                                     role TEXT NOT NULL
);

-- notes tablosu
CREATE TABLE IF NOT EXISTS notes (
                                     id INTEGER PRIMARY KEY AUTOINCREMENT,
                                     user_id INTEGER NOT NULL,
                                     title TEXT NOT NULL,
                                     content TEXT NOT NULL,
                                     CONSTRAINT fk_user FOREIGN KEY(user_id) REFERENCES users(id)
    );
