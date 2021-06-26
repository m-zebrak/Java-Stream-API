import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

public class StreamsExample {
    List<Employee> employees = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        Employee employee1 = new Employee("Oscarbait", "Custardbath", 18, List.of("PHP", "Java"));
        Employee employee2 = new Employee("Billybong", "Stinkyrash", 24, List.of("Python", "JavaScript"));
        Employee employee3 = new Employee("Benadryl", "Claritin", 30, List.of("HTML", "C++", "Java"));
        Employee employee4 = new Employee("Butercup", "Scratchnsniff", 12, List.of("PHP", "Java", "CSS", "HTML"));
        Employee employee5 = new Employee("Benadryl", "Humperdinck", 76, List.of("Ruby", "C#"));
        Employee employee6 = new Employee("Whippersnatch", "Scratchnsniff", 23, List.of("C++"));
        Employee employee7 = new Employee("Budapest", "Upperclass", 43, List.of("Python", "Java", "Ruby", "C#", "C++"));
        Employee employee8 = new Employee("Boilerdang", "Vegemite", 51, List.of("PHP"));

        employees.add(employee1);
        employees.add(employee2);
        employees.add(employee3);
        employees.add(employee4);
        employees.add(employee5);
        employees.add(employee6);
        employees.add(employee7);
        employees.add(employee8);
    }

    @Test
    public void printStream() {
        /*employees.stream()
                .forEach(employee -> System.out.println(employee));*/
        employees.forEach(System.out::println);
    }

    @Test
    public void mapOperation() {
        /*employees.stream()
                .map(employee -> employee.getFirstName())
                .forEach(System.out::println);*/

        employees.stream()
                .map(Employee::getFirstName)
                .forEach(System.out::println);

        System.out.println();

        employees.stream()
                .map(employee -> employee.getFirstName() + " " + employee.getLastName())
                .forEach(System.out::println);
    }

    @Test
    public void flatMapOperation() {
        List<List<String>> allSkills = employees.stream()
                .map(Employee::getSkills)
                .collect(Collectors.toList());

        System.out.println(allSkills);

        /*List<String> allSkills2 = employees.stream()
                .map(Employee::getSkills)
                .flatMap(list -> list.stream())
                .collect(Collectors.toList());*/

        List<String> allSkills2 = employees.stream()
                .map(Employee::getSkills)
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());

        System.out.println(allSkills2);
    }

    @Test
    public void filterOperation() {
        employees.stream()
                .filter(employee -> employee.getFirstName().startsWith("O"))
                .forEach(System.out::println);
    }

    @Test
    public void sortedOperation() {
        employees.stream()
                .sorted(Comparator.comparing(Employee::getAge))
                .forEach(System.out::println);
    }

    @Test
    public void limitOperation() {
        employees.stream()
                .sorted(Comparator.comparing(Employee::getFirstName))
                .limit(4)
                .forEach(System.out::println);
    }

    @Test
    public void skipOperation() {
        employees.stream()
                .sorted(Comparator.comparing(Employee::getFirstName))
                .skip(4)
                .forEach(System.out::println);
    }

    @Test
    public void countOperation() {
        long numberOfEmployees = employees.stream()
                .filter(employee -> employee.getFirstName().startsWith("B"))
                .count();

        System.out.println(numberOfEmployees);
    }

    @Test
    public void minMaxOperation() {
        Employee youngestEmployee = employees.stream()
                .min(Comparator.comparing(Employee::getAge)).get();

        Employee maxSkillsEmployee = employees.stream()
                .max(Comparator.comparing(employee -> employee.getSkills().size())).get();

        System.out.println(youngestEmployee);
        System.out.println(maxSkillsEmployee);
    }


    @Test
    public void findAnyFindFirstOperation() {
        Employee firstEmployeeWithB = employees.stream()
                .filter(employee -> employee.getFirstName().startsWith("B"))
                .findFirst().get();

        Employee anyEmployeeWithB = employees.stream()
                .filter(employee -> employee.getFirstName().startsWith("B"))
                .findAny().get();

        System.out.println(firstEmployeeWithB);
        System.out.println(anyEmployeeWithB);
    }

    @Test
    public void matchOperation() {
        boolean allEmployeesContainsA = employees.stream()
                .allMatch(employee -> employee.getFirstName().contains("a"));

        boolean anyEmployeeStartsWithW = employees.stream()
                .anyMatch(employee -> employee.getFirstName().startsWith("W"));

        boolean noEmployeeContainingX = employees.stream()
                .noneMatch(employee -> employee.getFirstName().contains("x"));

        System.out.println(allEmployeesContainsA);
        System.out.println(anyEmployeeStartsWithW);
        System.out.println(noEmployeeContainingX);
    }

    @Test
    public void reduceOperation() {
        /*Integer sumOfAllAges = employees.stream()
                .map(Employee::getAge)
                .reduce((age1, age2) -> age1 + age2).get();*/

        Integer sumOfAllAges = employees.stream()
                .map(Employee::getAge)
                .reduce(Integer::sum)
                .get();

        int sumOfAllAges2 = employees.stream()
                .map(Employee::getAge)
                .reduce(0, Integer::sum);

        int sumOfAllAges3 = employees.stream()
                .reduce(0, (age, employees) -> age + employees.getAge(), Integer::sum);

        System.out.println(sumOfAllAges);
        System.out.println(sumOfAllAges2);
        System.out.println(sumOfAllAges3);

        String allNames = employees.stream()
                .map(Employee::getFirstName)
                .reduce((name, name2) -> name + ", " + name2)
                .get();

        System.out.println(allNames);
    }

    @Test
    public void takeWhileOperation() {
        employees.stream()
                .sorted(Comparator.comparing(Employee::getAge))
                .takeWhile(employee -> employee.getAge() < 30)
                .forEach(System.out::println);
    }

    @Test
    public void dropWhileOperation() {
        employees.stream()
                .sorted(Comparator.comparing(Employee::getAge))
                .dropWhile(employee -> employee.getAge() < 30)
                .forEach(System.out::println);
    }

    @Test
    public void forEachOrdered() {
        String sentence = "Hello world";

        sentence.chars().forEach(value -> System.out.print((char) value));

        System.out.print("\nMultithreading: \t");
        sentence.chars().parallel().forEach(value -> System.out.print((char) value));

        System.out.print("\nMultithreading but ordeded: \t");
        sentence.chars().parallel().forEachOrdered(value -> System.out.print((char) value));
    }

    @Test
    //debug only, do not use for modification
    public void peekOperation() {
        List<Employee> newEmployees = employees.stream()
                .peek(employee -> employee.setFirstName("Kamil"))
                .collect(Collectors.toList());

        System.out.println(newEmployees);
        System.out.println();
        System.out.println(employees);
    }
}