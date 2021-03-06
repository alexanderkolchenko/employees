<%@ page import="com.example.employees.controller.config.ColumnsConfig" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Add Employee</title>
    <style>
        #container {
            font-family: arial, sans-serif;
            border-collapse: collapse;
            width: 200px;
            margin: auto;
            margin-top: 50px;
        }

        input {
            margin-bottom: 10px;
        }

        #main_page button, form button {
            width: 110px;
        }

        select {
            width: 177px;
            text-align: left;
        }


    </style>
</head>
<body>
<%
    String[] cities = ColumnsConfig.getCities();
    pageContext.setAttribute("cities", cities);

    String[] positions = ColumnsConfig.getPositions();
    pageContext.setAttribute("positions", positions);
%>

<div id="container">
    <form action="add_employee_servlet" method="post" id="form">
        <label for="fname">First name:</label><br>
        <input type="text" id="fname" name="name" required><br>

        <label for="lname">Last name:</label><br>
        <input type="text" id="lname" name="surname" required><br>

        <label for="position">Position:</label><br>
        <select id="position" form="form" name="position">
            <c:forEach var="position" items="${positions}">
                <option value="${position}">${position}</option>
            </c:forEach>
        </select>
        <br>
        <br>
        <label for="email">E-mail:</label><br>
        <input type="text" id="email" name="email" required><br>

        <label for="city">City:</label><br>
        <select id="city" form="form" name="city">
            <c:forEach var="city" items="${cities}">
                <option value="${city}">${city}</option>
            </c:forEach>
        </select>
        <br>
        <br>
        <button type="submit">Add Employee</button>
    </form>
    <a id="main_page" href="/employees_war_exploded">
        <button>Main Page</button>
    </a>
</div>
</body>
</html>
