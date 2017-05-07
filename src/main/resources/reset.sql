DROP TABLE IF EXISTS `events`;
DROP TABLE IF EXISTS `categories`;

CREATE TABLE IF NOT EXISTS `events` (id integer PRIMARY KEY AUTOINCREMENT, name text NOT NULL, date text NOT NULL, description text not null, category_id int NOT NULL, link text);
CREATE TABLE IF NOT EXISTS `categories` (id integer PRIMARY KEY AUTOINCREMENT, name text NOT NULL);