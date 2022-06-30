package com.example.employees.dao;

import com.example.employees.model.Employee;
import com.example.employees.model.EmployeeParamMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class EmployeeDao {
    private final static Logger log = LoggerFactory.getLogger(EmployeeDao.class);
    private ConnectionDB connectionDB;
    private static final String INSERT_EMPLOYEE = "INSERT INTO employee (e_name, e_surname, e_position, e_email, e_city, e_salary, e_hire_date) VALUES(?,?,?,?,?,?,?)";
    private static final String UPDATE_EMPLOYEE = "UPDATE employee SET e_name = ?, e_surname = ?, e_position = ?, e_email = ?, e_city = ?, e_salary = ?, e_hire_date = ? WHERE e_id =";
    private static final String GET_EMPLOYEES = "SELECT * FROM employee ORDER BY e_id";
    private static final String GET_COUNT_ROWS = "SELECT count(*) FROM employee";
    private static final String GET_EMPLOYEE_BY_ID = "SELECT * FROM employee WHERE e_id =";
    private static final String DELETE_EMPLOYEE_BY_ID = "DELETE FROM employee WHERE e_id = ";

    public EmployeeDao(ConnectionDB connection) {
        this.connectionDB = connection;
    }

    //todo error page
    public ArrayList<Employee> getEmployeesList() {

        ArrayList<Employee> employees = null;

        try (Connection connection = connectionDB.getConnection()) {
            employees = new ArrayList<>();
            PreparedStatement statement = connection.prepareStatement(GET_EMPLOYEES);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Employee e = setParametersInEmployeeFromStatement(new Employee(), rs);
                employees.add(e);
            }
        } catch (SQLException e) {
            log.error("Error of connection while getting employee: {} ", e.getMessage());
        }
        return employees;
    }

    public ArrayList<Employee> getEmployeesList(String column, String sortOrder, int offset, int limit) {

        ArrayList<Employee> employees = null;

        try (Connection connection = connectionDB.getConnection()) {
            employees = new ArrayList<>();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM employee ORDER BY " + column + " " + sortOrder +
                    " OFFSET ? LIMIT ?");

            statement.setInt(1, offset);
            statement.setInt(2, limit);

            System.out.println("default: " + statement);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Employee e = setParametersInEmployeeFromStatement(new Employee(), rs);
                employees.add(e);
            }
        } catch (SQLException e) {
            log.error("Error of connection while getting employee: {} ", e.getMessage());
        }
        return employees;
    }

    public ArrayList<Employee> getEmployeesByFilter2(EmployeeParamMapper mapper, int offset, int limit, String column, String order) {
        ArrayList<Employee> employees = null;

        try (Connection connection = connectionDB.getConnection()) {
            employees = new ArrayList<>();
            StringBuilder st = new StringBuilder("SELECT * FROM employee WHERE ");
            int count = 1;
            if (mapper.hasNames()) {
                st.append("e_name ILIKE (?) AND ");
            }
            if (mapper.hasSurnames()) {
                st.append("e_surname ILIKE (?) AND ");
            }
            if (mapper.hasEmails()) {
                st.append("e_email ILIKE (? AND ");
            }
            if (mapper.hasCities()) {
                st.append("e_city IN (");
                for (int i = 0; i < mapper.getCities().size(); i++) {
                    if (i == mapper.getCities().size() - 1) {
                        st.append(" ?)");
                    } else {
                        st.append("?,");
                    }
                }
                st.append(" AND ");
            }
            if (mapper.hasPositions()) {
                st.append("e_position IN (");
                for (int i = 0; i < mapper.getPositions().size(); i++) {
                    if (i == mapper.getPositions().size() - 1) {
                        st.append(" ?)");
                    } else {
                        st.append("?,");
                    }
                }
                st.append(" AND ");
            }
            if (mapper.hasSalary()) {
                st.append("e_salary BETWEEN ? AND ? AND ");
            }
            if (mapper.hasDate()) {
                st.append("e_hire_date BETWEEN ? AND ? AND ");
            }

            st.append("ORDER BY ");
            if (st.indexOf("AND ORDER") != -1) {
                st.replace(st.lastIndexOf("AND ORDER"), st.lastIndexOf("ORDER"), "");
            }
            st.append(column);
            st.append(" ");
            st.append(order);
            st.append(" OFFSET ");
            st.append(offset);
            st.append(" LIMIT ");
            st.append(limit);
            System.out.println("filter2 builder " + st);

            PreparedStatement statement = connection.prepareStatement(st.toString());

            if (mapper.hasNames()) {
                statement.setString(count++, mapper.getNames());
            }

            if (mapper.hasSurnames()) {
                statement.setString(count++, mapper.getSurnames());
            }

            if (mapper.hasEmails()) {
                statement.setString(count++, mapper.getSurnames());
            }

            if (mapper.hasCities()) {
                for (String city : mapper.getCities()) {
                    statement.setString(count++, city);
                }
            }
            if (mapper.hasPositions()) {
                for (String position : mapper.getPositions()) {
                    statement.setString(count++, position);
                }
            }
            if (mapper.hasSalary()) {
                statement.setBigDecimal(count++, mapper.getMinSalary());
                statement.setBigDecimal(count++, mapper.getMaxSalary());
            }

            if (mapper.hasDate()) {
                statement.setDate(count++, Date.valueOf(mapper.getMinHireDate()));
                statement.setDate(count++, Date.valueOf(mapper.getMaxHireDate()));
            }
            System.out.println("filter2 ready:" + statement.toString());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Employee e = setParametersInEmployeeFromStatement(new Employee(), rs);
                employees.add(e);
            }

        } catch (SQLException e) {
            log.error("Error of connection while getting employee: {} ", e.getMessage());
        }
        System.out.println(employees);
        return employees;

    }

    public ArrayList<Employee> getEmployeesByFilter(Map<String, String[]> params, int offset, int limit, String column, String order) {
        ArrayList<Employee> employees = null;

        try (Connection connection = connectionDB.getConnection()) {
            employees = new ArrayList<>();
            StringBuilder st = new StringBuilder("SELECT * FROM employee WHERE");

            ArrayList<String> flatParams = new ArrayList<>();
            for (Map.Entry<String, String[]> entry : params.entrySet()) {

                st.append(" ");
                st.append(entry.getKey());
                if (entry.getKey().equals("e_name") || entry.getKey().equals("e_surname") || entry.getKey().equals("e_email")) {
                    st.append(" ILIKE");
                } else if (entry.getKey().equals("e_salary")) {
                    st.append(" BETWEEN ? AND ? ");
                    flatParams.add(entry.getValue()[0].split(":")[0]);
                    flatParams.add(entry.getValue()[0].split(":")[1]);
                } else {
                    st.append(" IN");
                }
                st.append(" (");
                for (String s : entry.getValue()) {
                    if (!entry.getValue()[0].contains(":")) {
                        st.append(" ?,");
                        flatParams.add(s);
                    }
                }
                st.append(")");
                if (st.lastIndexOf(",)") != -1) {
                    st.replace(st.lastIndexOf(",)"), st.lastIndexOf(")"), "");
                }
                if (st.lastIndexOf("()") != -1) {
                    st.replace(st.lastIndexOf("()"), st.lastIndexOf("()") + 2, "");
                }
                if (params.size() > 1) {
                    st.append(" AND");
                }
            }
            st.append(" ORDER BY ");
            st.append(column);
            st.append(" ");
            st.append(order);
            st.append(" OFFSET ");
            st.append(offset);
            System.out.println(st);
            if (st.lastIndexOf("AND OFFSET") != -1) {
                st.replace(st.lastIndexOf("AND OFFSET"), st.lastIndexOf("OFFSET"), "");
            }
            if (st.lastIndexOf("AND ORDER") != -1) {
                st.replace(st.lastIndexOf("AND ORDER"), st.lastIndexOf("ORDER"), "");
            }
            st.append(" LIMIT ");
            st.append(limit);
            PreparedStatement statement = connection.prepareStatement(st.toString());

            for (int i = 0; i < flatParams.size(); i++) {
                statement.setString(i + 1, flatParams.get(i));
            }
            System.out.println(statement.toString());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Employee e = setParametersInEmployeeFromStatement(new Employee(), rs);
                employees.add(e);
            }
        } catch (SQLException e) {
            log.error("Error of connection while getting employee: {} ", e.getMessage());
        }
        return employees;
    }

    public int getCountRows() {

        int count = 0;

        try (Connection connection = connectionDB.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(GET_COUNT_ROWS);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            log.error("Error of connection while getting employee: {} ", e.getMessage());
        }
        return count;
    }

    public int getCountRowsByFilters(EmployeeParamMapper params) {
        return getEmployeesByFilter2(params, 0, Integer.MAX_VALUE, "e_id", "ASC").size();
    }


    public void addEmployees(Employee employee) throws SQLException {
        try (Connection connection = connectionDB.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(INSERT_EMPLOYEE);
            setParametersInStatementFromEmployee(statement, employee);
            statement.execute();
            //todo getGeneratedkey
            log.info("New employee created : name - {}, surname - {}, position - {}",
                    employee.getName(),
                    employee.getSurname(),
                    employee.getPosition());
        }
    }


    public Optional<Employee> getEmployeeByID(String id) {

        Employee employee = null;

        try (Connection connection = connectionDB.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(GET_EMPLOYEE_BY_ID + id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                employee = setParametersInEmployeeFromStatement(new Employee(), rs);
            }
        } catch (SQLException e) {
            log.error("Error of connection while getting employee: {} ", e.getMessage());
        }
        return Optional.ofNullable(employee);
    }

    public void updateEmployees(Employee employee) throws SQLException {

        Employee e = getEmployeeByID(String.valueOf(employee.getId())).orElseThrow(NoSuchElementException::new);

        try (Connection connection = connectionDB.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(UPDATE_EMPLOYEE + employee.getId());
            setParametersInStatementFromEmployee(statement, employee);
            statement.execute();
            String changes = compareEmployees(e, employee);
            if (!changes.equals("")) {
                log.info("Employee changed: database id - {}, fields: {} ", e.getId(), changes);
            }
        }
    }

    public void deleteEmployeeByID(String id) {

        Employee e = getEmployeeByID(id).orElseThrow(NoSuchElementException::new);

        try (Connection connection = connectionDB.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(DELETE_EMPLOYEE_BY_ID + e.getId());
            statement.execute();
            log.info("Employee deleted: name {}, surname - {}, position {}", e.getName(), e.getSurname(), e.getPosition());
        } catch (SQLException exc) {
            log.error("Error while deleting employee: {}", exc.getMessage());
        }
    }

    private static Employee setParametersInEmployeeFromStatement(Employee e, ResultSet rs) throws SQLException {
        e.setId(rs.getInt("e_id"));
        e.setName(rs.getString("e_name"));
        e.setSurname(rs.getString("e_surname"));
        e.setPosition(rs.getString("e_position"));
        e.setEmail(rs.getString("e_email"));
        e.setCity(rs.getString("e_city"));
        e.setSalary(rs.getBigDecimal("e_salary"));
        //todo reverse date
        e.setHireDate(rs.getDate("e_hire_date").toLocalDate());
        return e;
    }

    private static void setParametersInStatementFromEmployee(PreparedStatement s, Employee e) throws SQLException {
        s.setString(1, e.getName());
        s.setString(2, e.getSurname());
        s.setString(3, e.getPosition());
        s.setString(4, e.getEmail());
        s.setString(5, e.getCity());
        s.setBigDecimal(6, e.getSalary());
        s.setDate(7, Date.valueOf(e.getHireDate()));

    }

    //возращает строку состоящую из полей, который поменялись при редактировании работника
    private static String compareEmployees(Employee e1, Employee e2) {
        StringBuilder sb = new StringBuilder();
        if (!e1.getName().equals(e2.getName()))
            sb.append("before - ").append(e1.getName()).append(" after - ").append(e2.getName()).append("; ");
        if (!e1.getSurname().equals(e2.getSurname()))
            sb.append("before - ").append(e1.getSurname()).append(" after - ").append(e2.getSurname()).append("; ");
        if (!e1.getPosition().equals(e2.getPosition()))
            sb.append("before - ").append(e1.getPosition()).append(" after - ").append(e2.getPosition()).append("; ");
        if (!e1.getEmail().equals(e2.getEmail()))
            sb.append("before - ").append(e1.getEmail()).append(" after - ").append(e2.getEmail()).append("; ");
        if (!e1.getCity().equals(e2.getCity()))
            sb.append("before - ").append(e1.getCity()).append(" after - ").append(e2.getCity()).append("; ");
        if (!e1.getSalary().equals(e2.getSalary()))
            sb.append("before - ").append(e1.getSalary()).append(" after - ").append(e2.getSalary()).append("; ");
        if (!e1.getHireDate().equals(e2.getHireDate()))
            sb.append("before - ").append(e1.getHireDate()).append(" after - ").append(e2.getHireDate()).append("; ");

        return sb.toString();
    }
}
