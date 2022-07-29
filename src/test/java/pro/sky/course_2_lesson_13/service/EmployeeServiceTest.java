package pro.sky.course_2_lesson_13.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pro.sky.course_2_lesson_13.exception.EmployeeAlreadyAddedException;
import pro.sky.course_2_lesson_13.exception.EmployeeNotFoundException;
import pro.sky.course_2_lesson_13.model.Employee;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceTest {

    private final EmployeeService out = new EmployeeService();

    @ParameterizedTest
    @MethodSource("params")
    public void addTest(String firstName,
                        String lastName,
                        int department,
                        double salary) {
        Employee expected = new Employee(firstName, lastName, department, salary);
        assertEquals(expected, out.add(firstName, lastName, department, salary));

        assertThrows(EmployeeAlreadyAddedException.class,
                () -> out.add(firstName, lastName, department, salary));
    }

    @ParameterizedTest
    @MethodSource("params")
    public void removeTest(String firstName,
                           String lastName,
                           int department,
                           double salary) {
        Employee expected = new Employee(firstName, lastName, department, salary);
        assertEquals(expected, out.add(firstName, lastName, department, salary));
        assertEquals(expected, out.remove(firstName, lastName));
        assertThrows(EmployeeNotFoundException.class,
                () -> out.remove(firstName, lastName));
    }

    @ParameterizedTest
    @MethodSource("params")
    void find(String firstName,
              String lastName,
              int department,
              double salary) {
        assertThrows(EmployeeNotFoundException.class,
                () -> out.find(firstName, lastName));
        Employee expected = new Employee(firstName, lastName, department, salary);
        assertEquals(expected, out.add(firstName, lastName, department, salary));
        assertEquals(expected, out.find(firstName, lastName));
    }

    @Test
    void getEmployees() {
        List<Employee> expected = List.of(
                new Employee("Ivan", "Ivanov", 1, 50_500.5),
                new Employee("Petr", "Ivanov", 2, 66_030.3)
        );
        out.add("Ivan", "Ivanov", 1, 50_500.5);
        out.add("Petr", "Ivanov", 2, 66_030.3);
        assertEquals(expected, out.getEmployees());
    }

    @ParameterizedTest
    @MethodSource("paramsForCheckName")
    public void checkNameTest(String expected,
                              String a) {
        assertEquals(expected, out.checkName(a));
    }

    @ParameterizedTest
    @MethodSource("paramsNegativeForCheckName")
    public void shouldReturnRuntimeExceptionWhenCheckNameNotIsAlpha(String a) {
        assertThrows(RuntimeException.class,
                () -> out.checkName(a), "400 Bad request");
    }

    public static Stream<Arguments> params() {
        return Stream.of(
                Arguments.of("Ivan", "Ivanov", 1, 50_500.5),
                Arguments.of("Petr", "Ivanov", 2, 66_030.3),
                Arguments.of("Nikolay", "Ivanov", 3, 83_009.8)
        );
    }

    public static Stream<Arguments> paramsForCheckName() {
        return Stream.of(
                Arguments.of("Ivan", "Ivan"),
                Arguments.of("Ivan", "ivan"),
                Arguments.of("Ivan", "IVAN"),
                Arguments.of("Ivan", "iVAN"),
                Arguments.of("Иван", "ИваН"),
                Arguments.of("Ivan", "iVaN")
        );
    }

    public static Stream<Arguments> paramsNegativeForCheckName() {
        return Stream.of(
                Arguments.of("Iv an"),
                Arguments.of("Iv-an"),
                Arguments.of("&van"),
                Arguments.of("Iv/an"),
                Arguments.of("Iv0an"),
                Arguments.of("")
        );
    }
}
