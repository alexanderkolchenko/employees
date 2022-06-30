<%@ page import="com.example.employees.controller.config.ColumnsConfig" %>
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


        select {
            width: 177px;
            text-align: left;
        }
    </style>
</head>
<body>
<jsp:useBean id="employee" scope="request" type="com.example.employees.model.Employee"/>
<c:set var="employee" scope="request" value="${employee}"/>
<%
    String[] cities = ColumnsConfig.getCities();
    pageContext.setAttribute("cities", cities);

    String[] positions = ColumnsConfig.getPositions();
    pageContext.setAttribute("positions", positions);
%>
<div id="container">
    <form action="edit_employee_servlet/${employee.id}" method="post" id="form">
        <label for="fname">First name:</label><br>
        <input type="text" id="fname" name="name" value="<c:out value="${employee.name}" />" required><br>

        <label for="lname">Last name:</label><br>
        <input type="text" id="lname" name="surname" value="<c:out value="${employee.surname}" />" required><br>

        <label for="position">Position:</label><br>
        <select id="position" form="form" name="position">
            <c:forEach var="position" items="${positions}">
                <option value="${position}" ${position==employee.position?'selected':''}>${position}</option>
            </c:forEach>
        </select>
        <br>
        <br>
        <label for="email">E-mail:</label><br>
        <input type="text" id="email" name="email" value="<c:out value="${employee.email}" />" required><br>

        <label for="city">City:</label><br>
        <select id="city" form="form" name="city">
            <c:forEach var="city" items="${cities}">
                <option value="${city}" ${city==employee.city?'selected':''}>${city}</option>
            </c:forEach>
        </select>
        <br>
        <br>
        <input type="number" id="salary" name="salary" value="<c:out value="${employee.salary}"/>" required><br>
        <input type="date" id="hire_date" name="hire_date" value="<c:out value="${employee.hireDate}"/>" required><br>
        <br>
        <button type="submit">Save Change</button>
        <br>
        <a href="/employees_war_exploded/delete_employee_servlet/${employee.id}"><input id="delete" type="button"
                                                                                        value="Delete"
                                                                                        name="delete"></a>
    </form>
    <a id="main_page" href="/employees_war_exploded">
        <button>Main Page</button>
    </a>
</div>
</body>
</html>
