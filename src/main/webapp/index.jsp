<%@ page import="com.example.employees.dao.EmployeeDao" %>
<%@ page import="com.example.employees.model.Employee" %>
<%@ page import="com.example.employees.model.User" %>
<%@ page import="com.example.employees.dao.ConnectionDao" %>
<%@ page import="java.util.*" %>
<%@ page import="com.example.employees.controller.config.ColumnsConfig" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="com.example.employees.model.EmployeeParamMapper" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Employees</title>
    <link rel="stylesheet" href="styles/style.css">
</head>
<body>
<%!
    String[] columns = new String[]{"name", "surname", "position", "email", "city", "salary", "hire_date"};
    String column;
    String order;
    int limit = 5;
    int offset;
    int pages;
%>
<%

    EmployeeDao dao = new EmployeeDao(new ConnectionDao());
    int countRows = dao.getCountRows();

    if (countRows % limit != 0) {
        pages = countRows / limit + 1;
    } else {
        pages = countRows / limit;
    }
    int currentPage;

    if (request.getParameter("page") == null || request.getParameter("page").equals("")) {
        currentPage = 1;
        request.setAttribute("page", "1");
    } else {
        currentPage = Integer.parseInt(request.getParameter("page"));
    }
    request.setAttribute("currentPage", currentPage);

    if (request.getParameter("sorting") == null || request.getParameter("sorting").equals("")) {
        request.setAttribute("sorting", "id_asc");
    } else {
        request.setAttribute("sorting", request.getParameter("sorting"));
    }

    pageContext.setAttribute("pages", pages);
    offset = currentPage * 5 - 5;
    String currentSort = request.getParameter("sorting");
    if (currentSort != null) {
        column = "e_" + currentSort.substring(0, currentSort.lastIndexOf("_"));
        order = currentSort.substring(currentSort.lastIndexOf("_") + 1);
    } else {
        column = "e_id";
        order = "ASC";
    }

    String[] cities = ColumnsConfig.getCities();
    pageContext.setAttribute("cities", cities);

    String[] positions = ColumnsConfig.getPositions();
    pageContext.setAttribute("positions", positions);

    ArrayList<Employee> employees;
    if (request.getParameter("city") != null && !request.getParameter("city").equals("")
            || request.getParameter("position") != null && !request.getParameter("position").equals("")
            || request.getParameter("email") != null && !request.getParameter("email").equals("")
            || request.getParameter("name") != null && !request.getParameter("name").equals("")
            || request.getParameter("surname") != null && !request.getParameter("surname").equals("")
            || request.getParameter("salary") != null && !request.getParameter("salary").equals("")
            || request.getParameter("hire_date") != null && !request.getParameter("hire_date").equals("")) {

        EmployeeParamMapper params = EmployeeParamMapper.getInstance(request.getParameterMap());

        String s = request.getParameter("sorting");
        if (s != null) {
            String column = "e_" + s.substring(0, s.lastIndexOf("_"));
            String sort = s.substring(s.lastIndexOf("_") + 1);
            employees = dao.getEmployeesByFilter2(params, offset, limit, column, sort);
        } else {
            System.out.println("default filter");
            employees = dao.getEmployeesByFilter2(params, offset, limit, "e_id", "ASC");
        }

        countRows = dao.getCountRowsByFilters(params);
        out.print("id=count_row" + countRows);


    } else {
        employees = dao.getEmployeesList(column, order, offset, limit);
    }

    pageContext.setAttribute("employees", employees);
    String role = (String) request.getSession().getAttribute("role");
    String login = (String) request.getSession().getAttribute("login");
    Cookie cookie = Arrays.stream(request.getCookies()).filter(e -> e.getName().equals("userTime")).findFirst().orElse(null);

    if (role == null || role.equals(User.ROLE.UNKNOWN.toString()) || cookie == null) {
        response.sendRedirect("/employees_war_exploded/login.jsp");
    }
    request.setAttribute("login", login);
%>

<c:set var="login" scope="page" value="${login}"/>
<c:set var="sorting" scope="request" value="${sorting}"/>

<div class="users">
    <span>Вы вошли как <c:out value="${login}"/></span>
</div>
<table id="table">
    <tr>
        <th>#</th>
        <th>Имя
            <a href="index.jsp?page=${param.page}&sorting=name_asc"><img class="default_sort_buttons"
                                                                         src="icons/up_down.png"></a>
            <a href="index.jsp?page=${param.page}&sorting=name_desc"><img class="ascending_sort_buttons hidden_element"
                                                                          src="icons/up.png"></a>
            <a href="index.jsp?page=${param.page}&sorting=id_asc"><img class="descending_sort_buttons hidden_element"
                                                                       src="icons/down.png"></a>
            <br><br>
            <input type="text" id="employee_name" size="10">
        </th>
        <th>Фамилия
            <a href="index.jsp?page=${param.page}&sorting=surname_asc"><img class="default_sort_buttons"
                                                                            src="icons/up_down.png"></a>
            <a href="index.jsp?page=${param.page}&sorting=surname_desc"><img
                    class="ascending_sort_buttons hidden_element"
                    src="icons/up.png"></a>
            <a href="index.jsp?page=${param.page}&sorting=id_asc"><img class="descending_sort_buttons hidden_element"
                                                                       src="icons/down.png"></a>
            <br><br>
            <input type="text" id="employee_surname" size="10"/></th>
        <th>Должность
            <a href="index.jsp?page=${param.page}&sorting=position_asc"><img class="default_sort_buttons"
                                                                             src="icons/up_down.png"></a>
            <a href="index.jsp?page=${param.page}&sorting=position_desc"><img
                    class="ascending_sort_buttons hidden_element"
                    src="icons/up.png"></a>
            <a href="index.jsp?page=${param.page}&sorting=id_asc"><img class="descending_sort_buttons hidden_element"
                                                                       src="icons/down.png"></a>
            <br><br>
            <form id="form_position">
                <div class="multiselect">
                    <div class="selectBox" id="select_box_city">
                        <%--todo insert selected value, insert enum position--%>
                        <select>
                            <option></option>
                        </select>
                        <div class="overSelect"></div>
                    </div>
                    <div class="checkboxes" id="city_list">
                        <c:forEach var="position" items="${positions}">
                            <label for="${position}">
                                <input type="checkbox" class="position_checkbox" id="${position}"
                                       value="${position}"/>${position}</label>
                        </c:forEach>
                    </div>
                </div>
            </form>
        <th>E-mail
            <a href="index.jsp?page=${param.page}&sorting=email_asc"><img class="default_sort_buttons"
                                                                          src="icons/up_down.png"></a>
            <a href="index.jsp?page=${param.page}&sorting=email_desc"><img class="ascending_sort_buttons hidden_element"
                                                                           src="icons/up.png"></a>
            <a href="index.jsp?page=${param.page}&sorting=id_asc"><img class="descending_sort_buttons hidden_element"
                                                                       src="icons/down.png"></a>
            <br><br>
            <input type="text" id="employee_email" size="10"/>

        </th>
        <th>Город
            <a href="index.jsp?page=${param.page}&sorting=city_asc"><img class="default_sort_buttons"
                                                                         src="icons/up_down.png"></a>
            <a href="index.jsp?page=${param.page}&sorting=city_desc"><img class="ascending_sort_buttons hidden_element"
                                                                          src="icons/up.png"></a>
            <a href="index.jsp?page=${param.page}&sorting=id_asc"><img class="descending_sort_buttons hidden_element"
                                                                       src="icons/down.png"></a>
            <br><br>
            <form id="form_city" name="form_city">
                <div class="multiselect">
                    <div class="selectBox" id="select_box_position">
                        <select>
                            <option></option>
                        </select>
                        <div class="overSelect"></div>
                    </div>
                    <div class="checkboxes" id="position_list">
                        <c:forEach var="city" items="${cities}">
                            <label for="${city}">
                                <input type="checkbox" class="city_checkbox" id="${city}" value="${city}"/>${city}
                            </label>
                        </c:forEach>
                    </div>
                </div>
            </form>
        </th>
        <th>Salary
            <a href="index.jsp?page=${param.page}&sorting=salary_asc"><img class="default_sort_buttons"
                                                                           src="icons/up_down.png"></a>
            <a href="index.jsp?page=${param.page}&sorting=salary_desc"><img
                    class="ascending_sort_buttons hidden_element"
                    src="icons/up.png"></a>
            <a href="index.jsp?page=${param.page}&sorting=id_asc"><img class="descending_sort_buttons hidden_element"
                                                                       src="icons/down.png"></a>
            <br><br>

            <%--todo request to get max and min--%>
            <span style="font-size:10px">min</span> <input type="number" id="employee_salary_min" value="0" size="10"/>
            <span style="font-size:10px">max</span> <input type="number" id="employee_salary_max" value="500000"
                                                           size="10"/>

        </th>
        <th>Hire date
            <a href="index.jsp?page=${param.page}&sorting=hire_date_asc"><img class="default_sort_buttons"
                                                                              src="icons/up_down.png"></a>
            <a href="index.jsp?page=${param.page}&sorting=hire_date_desc"><img
                    class="ascending_sort_buttons hidden_element"
                    src="icons/up.png"></a>
            <a href="index.jsp?page=${param.page}&sorting=id_asc"><img class="descending_sort_buttons hidden_element"
                                                                       src="icons/down.png"></a>
            <br><br>
            <%--todo request to get max and min--%>
            <c:set var="now" value="<%= LocalDate.now() %>"/>
            <span style="font-size:10px">min</span><input type="date" value="1990-01-01" name="min_date"
                                                          id="employee_hire_date_min" size="10"/>
            <span style="font-size:10px">max</span><input type="date" value="${now}" name="max_date"
                                                          id="employee_hire_date_max" size="10"/>

        </th>
        <th class="edit_button"><a href="add_employee">
            <button class="add_btn btn"> + Add Employee</button>
        </a></th>
    </tr>
    <c:set var="page" scope="request" value="currentPage"/>
    <c:forEach var="employee" items="${employees}" step="1" varStatus="status">

        <tr>
            <td>${status.index+currentPage*5-4}</td>
            <td>${employee.name}</td>
            <td>${employee.surname}</td>
            <td>${employee.position}</td>
            <td>${employee.email}</td>
            <td>${employee.city}</td>
            <td class="td_salary">${employee.salary}</td>
            <td class="td_hire_date">${employee.hireDate}</td>

            <td class="edit_button">
                <a href="edit_employee_servlet/${employee.id}">
                    <button class="edit_btn btn" type="submit">Edit employee</button>
                </a>
            </td>
        </tr>
    </c:forEach>
</table>

<div id="pages_div">
    <c:forEach var="i" begin="1" end="${pages}">
        <a class="pages" href='index.jsp?page=<c:out value="${i}"/>&sorting=<c:out value="${sorting}"/>'><c:out
                value="${i}"/></a>
        <%--  <a class="pages" href=''><c:out
                  value="${i}"/></a>--%>
    </c:forEach>
</div>

<footer>
    <a href="cookie_employee_servlet">Cookie</a><br><br>
    <a href="send_file_servlet">Upload file</a><br>
    <a href="show_files_for_download_servlet">Download file</a><br><br>
    <a href="logout_servlet">Log out</a><br>
</footer>
<script type="text/javascript">
    let admin = '<%= request.getSession().getAttribute("role")%>';
    if (admin !== 'ADMIN') {
        let buttons = document.getElementsByClassName('btn');
        Array.prototype.forEach.call(buttons, function (el) {
            el.style.display = "none";
        })
    }

</script>
<script type="text/javascript" src="script/script_jdbc.js">
</script>
</body>

</html>
