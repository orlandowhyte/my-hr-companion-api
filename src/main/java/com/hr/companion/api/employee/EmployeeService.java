package com.hr.companion.api.employee;

import com.hr.companion.api.auth.CustomUserDetailsService;
import com.hr.companion.api.auth.RegisterRequest;
import com.hr.companion.api.auth.RegisterResponse;
import com.hr.companion.api.exception.EmployeeNotFoundException;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final CustomUserDetailsService userService;

    /**
     * Retrieves all employees from the database.
     * @return List of Employees.
     */
    public List<EmployeeResponse> getAllEmployees() {
        log.info("Returned {} employees", employeeRepository.count());
        return employeeMapper.toResponseList(employeeRepository.findAll());
    }

    /**
     * Creates a new employee in the database.
     * @param request EmployeeRequest containing details of the employee to be created.
     * @return EmployeeResponse representing the newly created employee.
     */
    public EmployeeResponse createEmployee(EmployeeRequest request) {
        log.info("Creating employee: {}", request.getFirstName());
        Employee savedEmployee = employeeRepository.save(employeeMapper.toEntity(request));
        log.info("Employee created with ID: {}", savedEmployee.getEmployeeId());

        //Register user for the employee
        log.info("Creating user for employee with ID: {}", savedEmployee.getEmployeeId());
        RegisterRequest registerRequest = RegisterRequest.builder()
                .username(request.getEmailAddress())
                .firstname(request.getFirstName())
                .lastname(request.getLastName())
                .password("defaultPassword123")
                .email(request.getEmailAddress())
                .role("USER")
                .build();

        RegisterResponse registerResponse = userService.registerUser(registerRequest);
        log.info("User created for employee ID: {} with username: {}", savedEmployee.getEmployeeId(), registerRequest.getUsername());
        return employeeMapper.toResponse(savedEmployee);
    }

    /**
     * Retrieves an employee by its ID.
     * @param employeeId UUID representing the unique identifier of the employee.
     * @return EmployeeResponse representing the employee with the specified ID.
     * @throws EmployeeNotFoundException if the employee is not found.
     */
    public EmployeeResponse getEmployeeById(UUID employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with ID: " + employeeId));
        return employeeMapper.toResponse(employee);
    }
}
