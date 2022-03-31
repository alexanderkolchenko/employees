<%@ page import="com.example.employees.dao.EmployeeDao" %>
<%@ page import="com.example.employees.model.Employee" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.example.employees.model.User" %>
<%@ page import="java.util.Arrays" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>Employees</title>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <style>
        table, .users {
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

    String role = (String) request.getSession().getAttribute("role");
    String login = (String) request.getSession().getAttribute("login");

    Cookie cookie = Arrays.stream(request.getCookies()).filter(e -> e.getName().equals("userTime")).findFirst().orElse(null);

    if (role == null || role.equals(User.ROLE.UNKNOWN.toString()) || cookie == null) {
        response.sendRedirect("/employees_war_exploded/login.jsp");
    }
    request.setAttribute("login", login);
%>

<c:set var="login" scope="request" value="${login}"/>
<div class="users">
    <span>Вы вошли как <c:out value="${login}"/></span>
</div>

<table>
    <tr>
        <th>#</th>
        <th>Имя</th>
        <th>Фамилия</th>
        <th>Должность</th>
        <th>E-mail</th>
        <th>Город</th>
        <th class="edit_button"><a href="add_employee">
            <button class="add_btn btn"> + Add Employee</button>
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
                    <button class="edit_btn btn" type="submit">Edit employee</button>
                </a>
            </td>
        </tr>
    </c:forEach>
</table>


<a href="cookie_employee_servlet">Cookie</a><br>
<a href="send_file_servlet">Send file</a><br>
<a href="logout_servlet">Log out</a><br>

<script type="text/javascript">

    let admin = '<%= request.getSession().getAttribute("role")%>';
    if(admin!=='ADMIN') {
        let buttons = document.getElementsByClassName('btn');
        Array.prototype.forEach.call(buttons, function (el) {
            el.style.display = "none";
        })
    }

</script>
</body>

</html>
