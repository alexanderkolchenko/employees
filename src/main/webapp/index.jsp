<%@ page import="com.example.employees.dao.EmployeeDao" %>
<%@ page import="com.example.employees.model.Employee" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>Employees</title>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <style>
        table {
            font-family: arial, sans-serif;
            border-collapse: collapse;
            width: 80%;
            margin: auto;
            margin-top: 30px;
        }

        td, th {
            border: 1px solid #dddddd;
            text-align: left;
            padding: 8px;
        }
        button {
            width: 120px;
        }
        .edit_button {
            border: none;
        }
    </style>
</head>
<body>
<%
    ArrayList<Employee> employees = EmployeeDao.getEmployeesList();
    request.setAttribute("employees", employees);
%>
<table>
    <tr>
        <th>#</th>
        <th>Имя</th>
        <th>Фамилия</th>
        <th>Должность</th>
        <th>E-mail</th>
        <th>Город</th>
        <th class="edit_button "><a href="add_employee">
            <button class="add_btn"> + Add Employee</button>
        </a></th>
    </tr>

    <c:forEach var="employee" items="${employees}" step="1" varStatus="status">
        <tr>
            <td>${status.index+1}</td>
            <td>${employee.name}</td>
            <td>${employee.surname}</td>
            <td>${employee.position}</td>
            <td>${employee.email}</td>
            <td>${employee.city}</td>

            <td class="edit_button">
                    <a href="edit_employee_servlet/${employee.id}">
                        <button class="edit_btn" type="submit">Edit employee</button>
                    </a>
            </td>
        </tr>
    </c:forEach>
</table>


</body>
</html>