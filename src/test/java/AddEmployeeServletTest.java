import com.example.employees.controller.AddEmployeeServlet;
import com.example.employees.model.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.mockito.Mockito.*;


public class AddEmployeeServletTest {

    private static HttpServletRequest request;
    private static Employee employee;


    @BeforeAll
    public static void createEmployee() {
        employee = new Employee();
        employee.setName("John");
        employee.setSurname("Smith");
        employee.setCity("Moscow");
        employee.setPosition("engineer");
        employee.setEmail("smith@mail.com");

        request = mock(HttpServletRequest.class);
        when(request.getParameter("surname")).thenReturn("Smith");
        when(request.getParameter("city")).thenReturn("Moscow");
        when(request.getParameter("position")).thenReturn("engineer");
        when(request.getParameter("email")).thenReturn("smith@mail.com");
    }

    @Test
    public void shouldCreateEmployee() {
        when(request.getParameter("name")).thenReturn("John");
        Employee e = AddEmployeeServlet.createEmployeeFromRequest(request);
        Assertions.assertEquals(employee, e);
    }

    @Test
    public void shouldThrowExceptionWhenOneParameterIsNull() throws IOException {
        when(request.getParameter("name")).thenReturn(null);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Assertions.assertThrows(RuntimeException.class, () -> {
            AddEmployeeServlet.createEmployeeFromRequest(request);
        });
    }
}
