DROP TABLE IF EXISTS employee;

CREATE TABLE employee
(
    e_id        SERIAL PRIMARY KEY,
    e_name      VARCHAR(100) NOT NULL,
    e_surname   VARCHAR(100) NOT NULL,
    e_position  VARCHAR(50)  NOT NULL,
    e_email     VARCHAR(50)  NOT NULL UNIQUE,
    e_city      VARCHAR(50)  NULL,
    e_salary    numeric      NULL,
    e_hire_date date         NULL
);
INSERT INTO employee(e_name, e_surname, e_position, e_email, e_city, e_salary, e_hire_date)
VALUES ('Jose', 'Amar', 'Developer', 'JoseJAmar@mail.ru', 'Moscow', 200000, '30-12-2017');
INSERT INTO employee(e_name, e_surname, e_position, e_email, e_city, e_salary, e_hire_date)
VALUES ('Raymond', 'Hauck', 'Junior Developer', 'RaymondLHauck@mail.ru', 'Moscow', 60000, '12-12-2021');