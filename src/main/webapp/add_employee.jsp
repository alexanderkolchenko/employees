<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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


    </style>
</head>
<body>


<div id="container">
    <form action="add_employee_servlet" method="post">
        <label for="fname">First name:</label><br>
        <input type="text" id="fname" name="name" required ><br>

        <label for="lname">Last name:</label><br>
        <input type="text" id="lname" name="surname" required><br>

        <label for="position">Position:</label><br>
        <input type="text" id="position" name="position" required><br>

        <label for="email">E-mail:</label><br>
        <input type="text" id="email" name="email" required><br>

        <label for="city">City:</label><br>
        <input type="text" id="city" name="city" required><br>

        <button type="submit">Add Employee</button>
    </form>
    <a id="main_page" href="/employees_war_exploded">
        <button>Main Page</button>
    </a>
</div>
</body>
</html>
