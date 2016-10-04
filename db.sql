--CREATE TABLE person
--(
--   id SERIAL PRIMARY KEY,
--   name VARCHAR(25),
--   age INTEGER
--);

DROP TABLE IF EXISTS utilisateur;
CREATE TABLE utilisateur (
	id SERIAL PRIMARY KEY,
	nom VARCHAR (25),
	prenom VARCHAR (30),
	email VARCHAR (20),
	login VARCHAR (20),
	mdp VARCHAR (20)
 );