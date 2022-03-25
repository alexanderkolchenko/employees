drop table if exists employee;
create table employee
(
    e_id serial PRIMARY KEY,
    e_name varchar(100) NOT NULL,
    e_surname varchar(100) NOT NULL,
    e_position varchar(50) NOT NULL,
    e_email varchar(50) NOT NULL UNIQUE,
    e_city varchar(50) NULL
)
;


insert into employee(e_name, e_surname, e_position, e_email, e_city)
values('Ivan', 'Popov', 'Developer', 'ipopov@mail.ru', 'Moskow');
insert into employee(e_name, e_surname, e_position, e_email, e_city)
values('Natalia', 'Ivanova', 'Accounter', 'aivanova@mail.ru', 'St.Peterburg');
insert into employee(e_name, e_surname, e_position, e_email, e_city)
values('Svetlana', 'Zaharova', 'Junior Developer', 'szaharova@mail.ru', 'Moskow');
insert into employee(e_name, e_surname, e_position, e_email, e_city)
values('Sergej', 'Mihalkov', 'Manager', 'smihalkov@mail.ru', 'Samara');
insert into employee(e_name, e_surname, e_position, e_email, e_city)
values('John', 'Smith', 'Developer', 'jsmith@mail.ru', 'Volgograd');
insert into employee(e_name, e_surname, e_position, e_email, e_city)
values('Gosha', 'Petrov', 'Senior Developer', 'ipetrov@mail.ru', 'Moskow');
