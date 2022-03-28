<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 24.03.2022
  Time: 16:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Edit Employee</title>
    <style>
        #container {
            font-family: arial, sans-serif;
            border-collapse: collapse;
            width: 200px;
            margin: auto;
            margin-top: 50px;
            margin-bottom: 0;
        }

        form {
            margin-bottom: 0;
        }

        input {
            margin-bottom: 10px;
        }

        #main_page button, form button, #delete {
            width: 110px;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
<c:set var="employee" scope="request" value="${employee}"/>
<div id="container">
    <form action="edit_employee_servlet/${employee.id}" method="post">
        <label for="fname">First name:</label><br>
        <input type="text" id="fname" name="name" value="<c:out value="${employee.name}" />" required><br>

        <label for="lname">Last name:</label><br>
        <input type="text" id="lname" name="surname" value="<c:out value="${employee.surname}" />" required><br>

        <label for="position">Position:</label><br>
        <input type="text" id="position" name="position" value="<c:out value="${employee.position}" />" required><br>

        <label for="email">E-mail:</label><br>
        <input type="text" id="email" name="email" value="<c:out value="${employee.email}" />" required><br>

        <label for="city">City:</label><br>
        <input type="text" id="city" name="city" value="<c:out value="${employee.city}" />" required><br>

        <button type="submit">Save Change</button>
        <br>
        <a href="/employees_war_exploded/delete_employee_servlet/${employee.id}"><input id="delete" type="button" value="Delete" name="delete"></a>
    </form>
    <a id="main_page" href="/employees_war_exploded">
        <button>Main Page</button>
    </a>
</div>
</body>
</html>
