CREATE TABLE IF NOT EXISTS events (id SERIAL PRIMARY KEY, name text NOT NULL, date text NOT NULL, description text not null, category_id int NOT NULL, link text);
CREATE TABLE IF NOT EXISTS categories (id SERIAL PRIMARY KEY, name text NOT NULL);

INSERT INTO events (name, date, description, category_id, link) VALUES ('Codecool Cinema', '13/05/2017 18:30', 'Legendary assassin John Wick (Keanu Reeves) retired from his violent career after marrying the love of his life. Her sudden death leaves John in deep mourning. When sadistic mobster Iosef Tarasov (Alfie Allen) and his thugs steal John''s prized car and kill the puppy that was a last gift from his wife, John unleashes the remorseless killing machine within and seeks vengeance. Meanwhile, Iosef''s father (Michael Nyqvist) -- John''s former colleague -- puts a huge bounty on John''s head. 
Release date: December 5, 2014 (Poland) Directors: Chad Stahelski, David Leitch Box office: 88.8 million USD Budget: 30 million USD Wife: Helen', 1, 'https://www.google.com/');
INSERT INTO events (name, date, description, category_id, link) VALUES ('CoolHack', '25/05/2017 09:00', 'Lets test each others creativity!!111oneoneone', 2, 'https://www.google.com/');
INSERT INTO events (name, date, description, category_id, link) VALUES ('Testing2', '12/05/2017 15:15', 'It''s some "kind" of magic', 3, 'https://www.google.com/');
INSERT INTO events (name, date, description, category_id, link) VALUES ('Hello Java', '12/05/2017 12:30', 'We''re starting to learn Java, using Atom. Yes Atom. You need to know your shit before you start using that piece of heavy artillery called intelliJ', 3, 'https://www.google.com');

INSERT INTO categories (name) VALUES ('Entertainment');
INSERT INTO categories (name) VALUES ('Hackathon');
INSERT INTO categories (name) VALUES ('Java');
INSERT INTO categories (name) VALUES ('Python');
INSERT INTO categories (name) VALUES ('C++');
INSERT INTO categories (name) VALUES ('Embedded');
INSERT INTO categories (name) VALUES ('Linux');
INSERT INTO categories (name) VALUES ('Front-end');
INSERT INTO categories (name) VALUES ('Back-end');
INSERT INTO categories (name) VALUES ('Inspires');