package be.unamur.hermes.business.service;

import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import be.unamur.hermes.business.exception.BusinessException;
import be.unamur.hermes.dataaccess.entity.Address;
import be.unamur.hermes.dataaccess.entity.Employee;
import be.unamur.hermes.web.WebApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class)
public class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @Test(expected = BusinessException.class)
    public void test_register_employee_missing_password() {
	Employee employee = initEmployee();
	employeeService.register(employee);
    }

    @Test
    public void test_register_employee_ok() {
	Employee employee = initEmployee();
	employee.setPassword("myPassword");
	long employeeId = employeeService.register(employee);
	assertTrue(employeeId > 0);
    }

    private Employee initEmployee() {
	Address address = new Address();
	address.setCountry("Belgium");
	address.setMunicipality("Roux");
	address.setStreet("Rue de l'Eglise");
	address.setZipCode(5890);
	address.setStreetNb(5);

	return new Employee(0L, "Thomas", "Elskens", address, "thomaselskens@hotmail.com", "0472245565", "8311131158",
		LocalDate.of(1983, 11, 13), "00189153158", LocalDate.now(), 'M', "bachelor", 0, 0);
    }

}
