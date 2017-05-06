DROP TABLE IF EXISTS `events`;
DROP TABLE IF EXISTS `categories`;

CREATE TABLE `events` (id integer PRIMARY KEY AUTOINCREMENT, name text NOT NULL, date text NOT NULL, description text not null, category_id int NOT NULL, link text);
CREATE TABLE `categories` (id integer PRIMARY KEY AUTOINCREMENT, name text NOT NULL);

insert into `events`(name, date, description, category_id, link) VALUES ('Codecool Cinema', '9-05-2017', "We're gonna watch John Wick",1 ,'https://www.coodecool.pl/' );
insert into `events`(name, date, description, category_id, link) VALUES ('CoolHack', '11-06-2017', "We're gonna hack ths shit",2 ,'https://www.coodecool.pl/' );

INSERT INTO `categories` (name) VALUES ('Entertainment');
INSERT INTO `categories` (name) VALUES ('GroupEvent');