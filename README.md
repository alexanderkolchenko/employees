###Employee library
---
Веб-приложение реализует добавление, редатирование и удаление работников

JDK 8, Maven 3, Servlet, JSP, JDBC, PostgreSQL, Tomcat 9.0.60

Open http://localhost:8080/employees_war_exploded/ in a web browser.

---
* добавлена форма для аутентификации пользователя и кнопка выхода из учетной записи
* пользователи делятся две роли, обычные и админ. Обычные могут только просматривать справочник, админу доступно редактирование справочника
    * Login: admin, Password: admin - роль админа
    * Login: user, Password: user - роль пользователя
* дополнительно:
    * форма отправки файла на сервер
    * форма получения списка файлов для скачивания
    * ссылка на список cookies

