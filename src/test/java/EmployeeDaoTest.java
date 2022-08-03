
import com.example.employees.dao.ConnectionDB;
import com.example.employees.dao.EmployeeDao;
import com.example.employees.model.Employee;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Collectors;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EmployeeDaoTest {

    private EmployeeDao employeeDao;
    private Employee employee;

    @BeforeAll
    public void createEmployee() {
        employee = new Employee();
        employee.setName("Tom");
        employee.setSurname("Bolton");
        employee.setEmail("bolton@mail.com");
        employee.setPosition("tester");
        employee.setCity("NY");
        employee.setSalary(BigDecimal.valueOf(50000));
        employee.setHireDate(LocalDate.of(2015, 12, 11));
    }

    @BeforeEach
    public void setUp() throws URISyntaxException, IOException, SQLException {

        ConnectionDB connectionDB = new ConnectionDBTest();
        employeeDao = new EmployeeDao(connectionDB);

        URL database = EmployeeDaoTest.class.getClassLoader().getResource("schema.sql");
        String sql = Files.readAllLines(Paths.get(database.toURI())).stream().collect(Collectors.joining());
        try (Connection connection = connectionDB.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        }
    }

    @Test
    public void shouldAddEmployee() throws SQLException {
        employeeDao.addEmployees(employee);
        Assertions.assertEquals(3, employeeDao.getEmployeesList().size());
    }

    @Test
    public void shouldDeleteEmployee() {
        employeeDao.deleteEmployeeByID("1");
        Assertions.assertEquals(Optional.empty(), employeeDao.getEmployeeByID("1"));
        Assertions.assertEquals(1, employeeDao.getEmployeesList().size());
    }

    @Test
    public void shouldEditEmployee() throws SQLException {
        Employee newEmployee = new Employee();
        newEmployee.setId(1);
        newEmployee.setName("Tomas");
        newEmployee.setSurname("Bolton");
        newEmployee.setEmail("bolton@mail.com");
        newEmployee.setPosition("tester");
        newEmployee.setCity("NY");
        newEmployee.setSalary(BigDecimal.valueOf(17000));
        newEmployee.setHireDate(LocalDate.of(2015, 12, 25));
        employeeDao.updateEmployees(newEmployee);
        Assertions.assertEquals("Tomas", employeeDao.getEmployeeByID("1").get().getName());
    }
}
