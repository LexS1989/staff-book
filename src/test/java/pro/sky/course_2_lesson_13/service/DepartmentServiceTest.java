package pro.sky.course_2_lesson_13.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.course_2_lesson_13.exception.EmployeeNotFoundException;
import pro.sky.course_2_lesson_13.model.Employee;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private DepartmentService out;

    @BeforeEach
    public void listEmployees() {
        List<Employee> employees = List.of(
                new Employee("Ivan", "Ivanov", 1, 50_000),
                new Employee("Petr", "Ivanov", 1, 66_000),
                new Employee("Nikolay", "Ivanov", 1, 83_000),
                new Employee("Vasiliy", "Ivanov", 2, 68_000),
                new Employee("Ruslan", "Ivanov", 2, 59_000),
                new Employee("Alexandr", "Ivanov", 2, 87_000)
        );
        when(employeeService.getEmployees())
                .thenReturn(employees);
    }

    @ParameterizedTest
    @MethodSource("paramsMaxSalaryFromDepartment")
    public void employeeWithMaxSalaryFromDepartmentPositiveTest(int department, Employee expected) {
        assertThat(out.findEmployeeWithMaxSalaryFromDepartment(department)).isEqualTo(expected);
    }

    @Test
    public void employeeWithMaxSalaryFromDepartmentNegativeTest() {
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> out.findEmployeeWithMaxSalaryFromDepartment(3));
    }

    @ParameterizedTest
    @MethodSource("paramsMinSalaryFromDepartment")
    public void employeeWithMinSalaryFromDepartmentPositiveTest(int department, Employee expected) {
        assertThat(out.findEmployeeWithMinSalaryFromDepartment(department)).isEqualTo(expected);
    }

    @Test
    public void employeeWithMinSalaryFromDepartmentNegativeTest() {
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> out.findEmployeeWithMinSalaryFromDepartment(3));
    }

    @ParameterizedTest
    @MethodSource("paramsForFindEmployeesFromDepartment")
    public void findEmployeesFromDepartmentTest(int department, List<Employee> expected) {
        assertThat(out.findEmployeesFromDepartment(department)).isEqualTo(expected);
    }

    @Test
    public void findEmployeesTest() {
        assertThat(out.findEmployees()).containsAllEntriesOf(
                Map.of(
                        1, List.of(new Employee("Ivan", "Ivanov", 1, 50_000),
                                new Employee("Petr", "Ivanov", 1, 66_000),
                                new Employee("Nikolay", "Ivanov", 1, 83_000)),
                        2, List.of(new Employee("Vasiliy", "Ivanov", 2, 68_000),
                                new Employee("Ruslan", "Ivanov", 2, 59_000),
                                new Employee("Alexandr", "Ivanov", 2, 87_000))
                )
        );
    }

    public static Stream<Arguments> paramsMaxSalaryFromDepartment() {
        return Stream.of(
                Arguments.of(1, new Employee("Nikolay", "Ivanov", 1, 83_000)),
                Arguments.of(2, new Employee("Alexandr", "Ivanov", 2, 87_000))
        );
    }

    public static Stream<Arguments> paramsMinSalaryFromDepartment() {
        return Stream.of(
                Arguments.of(1, new Employee("Ivan", "Ivanov", 1, 50_000)),
                Arguments.of(2, new Employee("Ruslan", "Ivanov", 2, 59_000))
        );
    }

    public static Stream<Arguments> paramsForFindEmployeesFromDepartment() {
        return Stream.of(
                Arguments.of(1, List.of(new Employee("Ivan", "Ivanov", 1, 50_000),
                        new Employee("Petr", "Ivanov", 1, 66_000),
                        new Employee("Nikolay", "Ivanov", 1, 83_000))
                ),
                Arguments.of(2, List.of(new Employee("Vasiliy", "Ivanov", 2, 68_000),
                        new Employee("Ruslan", "Ivanov", 2, 59_000),
                        new Employee("Alexandr", "Ivanov", 2, 87_000))
                ),
                Arguments.of(3, Collections.emptyList())
        );
    }
}