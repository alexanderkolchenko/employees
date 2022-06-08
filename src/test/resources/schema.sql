DROP TABLE IF EXISTS employee;

CREATE TABLE employee
(
    e_id       SERIAL PRIMARY KEY,
    e_name     VARCHAR(100) NOT NULL,
    e_surname  VARCHAR(100) NOT NULL,
    e_position VARCHAR(50)  NOT NULL,
    e_email    VARCHAR(50)  NOT NULL UNIQUE,
    e_city     VARCHAR(50)  NULL
);

INSERT INTO employee (e_name, e_surname, e_position, e_email, e_city)
VALUES ('John', 'Smith', 'developer', 'smith@gmail.com', 'Moscow');

INSERT INTO employee (e_name, e_surname, e_position, e_email, e_city)
VALUES ('Ivan', 'Petrov', 'tester', 'petrov@gmail.com', 'St. Petersburg')