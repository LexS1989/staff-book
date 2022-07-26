package pro.sky.course_2_lesson_13.service;

import org.springframework.stereotype.Service;
import pro.sky.course_2_lesson_13.exception.EmployeeNotFoundException;
import pro.sky.course_2_lesson_13.model.Employee;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    private final EmployeeService employeeService;

    public DepartmentService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public Employee findEmployeeWithMaxSalaryFromDepartment(int department) {
        return employeeService.getEmployees().stream()
                .filter(employee -> employee.getDepartment() == department)
                .max((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary()))
                .orElseThrow(() -> new EmployeeNotFoundException());
    }

    public Employee findEmployeeWithMinSalaryFromDepartment(int department) {
        return employeeService.getEmployees().stream()
                .filter(employee -> employee.getDepartment() == department)
                .min((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary()))
                .orElseThrow(() -> new EmployeeNotFoundException());
    }

    public List<Employee> findEmployeesFromDepartment(int department) {
        return employeeService.getEmployees().stream()
                .filter(employee -> employee.getDepartment() == department)
                .collect(Collectors.toList());
    }

    public Map<Integer, List<Employee>> findEmployees() {
        return employeeService.getEmployees().stream()
                .collect(Collectors.groupingBy(employee -> employee.getDepartment()));
    }
}
