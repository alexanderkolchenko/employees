DROP TABLE IF EXISTS employee;
CREATE TABLE employee
(
    e_id SERIAL PRIMARY KEY,
    e_name VARCHAR(100) NOT NULL,
    e_surname VARCHAR(100) NOT NULL,
    e_position VARCHAR(50) NOT NULL,
    e_email VARCHAR(50) NOT NULL UNIQUE,
    e_city VARCHAR(50) NULL,
    e_salary numeric NULL,
    e_hire_date date NULL
);

select  * from employee;