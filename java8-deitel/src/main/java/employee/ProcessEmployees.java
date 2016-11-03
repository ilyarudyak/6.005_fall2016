package employee;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by ilyarudyak on 11/3/16.
 */
public class ProcessEmployees {

    private static final List<Employee> EMPLOYEE_LIST =
            Arrays.asList(
                new Employee("Jason", "Red", 5000, "IT"),
                new Employee("Ashley", "Green", 7600, "IT"),
                new Employee("Matthew", "Indigo", 3587.5, "Sales"),
                new Employee("James", "Indigo", 4700.77, "Marketing"),
                new Employee("Luke", "Indigo", 6200, "IT"),
                new Employee("Jason", "Blue", 3200, "Sales"),
                new Employee("Wendy", "Brown", 4236.4, "Marketing")
            );

    private static final Comparator<Employee> lastThenFirst = (e1, e2) -> {

        if (!e1.getLastName().equals(e2.getLastName())) {
            return e1.getLastName().compareTo(e2.getLastName());
        } else {
            return e1.getFirstName().compareTo(e2.getFirstName());
        }
    };

    public void printAllEmployees() {
        EMPLOYEE_LIST.stream().forEach(e -> System.out.println(e));
    }

    public void printWithinSalaryRange(double min, double max) {

        Predicate<Employee> fourToSixThousand =
                e -> (e.getSalary() >= min && e.getSalary() <= max);

        EMPLOYEE_LIST.stream()
                // (com-1) we can define comparator separately and then reuse it
                .filter(fourToSixThousand)
                .forEach(System.out::println);

        System.out.println("first in this range:");
        System.out.println(EMPLOYEE_LIST.stream()
                .filter(fourToSixThousand)
                // (new-met-1) new method
                .findFirst().get());
    }

    public void sortByLastNameThenFirst() {

        EMPLOYEE_LIST.stream()
                .sorted(lastThenFirst)
                .forEach(System.out::println);

        System.out.println("\n now in reversed order");

        EMPLOYEE_LIST.stream()
                // (com-2) we can reverse comparator
                .sorted(lastThenFirst.reversed())
                .forEach(System.out::println);

    }

    public void uniqueLastNames() {

        EMPLOYEE_LIST.stream()
                .map(e -> e.getLastName())
                // (new-met-2) new method
                .distinct()
//                .collect(Collectors.toSet())
//                .stream()
                .sorted()
                .forEach(System.out::println);
    }

    public void namesSortedLastThenFirst() {

        EMPLOYEE_LIST.stream()
                .sorted(lastThenFirst)
                .map(Employee::getName)
                .forEach(System.out::println);
    }

    public void groupByDepartment() {

//        EMPLOYEE_LIST.stream()
//                .map(Employee::getDepartment)
//                .distinct()
//                .forEach(d -> {
//                    System.out.println(d);
//                    EMPLOYEE_LIST.stream()
//                            .filter(e -> e.getDepartment().equals(d))
//                            .forEach(e -> System.out.println("\t" + e));
//                        }
//                );

        EMPLOYEE_LIST.stream()
                // (col-1) we may collect into map with groupingBy()
                .collect(Collectors.groupingBy(Employee::getDepartment))
                .forEach( (department, employees) -> {
                    System.out.println(department);
                    employees.forEach(employee -> System.out.println("\t" + employee));
                });
    }

    public void countByDepartments() {
//        EMPLOYEE_LIST.stream()
//                .collect(Collectors.groupingBy(Employee::getDepartment))
//                .forEach( (department, employees) -> {
//                    System.out.print(department + ":");
//                    System.out.println(employees.stream().count());
//                });

        EMPLOYEE_LIST.stream()
                // (col-2) we may group and then count
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()))
                .forEach( (department, count) -> {
                    System.out.print(department + ":");
                    System.out.println(count);
                });
    }

    public void sumSalaries() {

        System.out.println(EMPLOYEE_LIST.stream()
                // we don't have sum() on a general stream
                .mapToDouble(Employee::getSalary)
                .sum());

        System.out.println(EMPLOYEE_LIST.stream()
                // again we have to use double stream
                .mapToDouble(Employee::getSalary)
                .reduce(0, (acc, salary) -> acc + salary));
    }

    public void averageSalaries() {
        System.out.printf("%.2f%n", EMPLOYEE_LIST.stream()
                .mapToDouble(Employee::getSalary)
                .average()
                .getAsDouble());
    }

    public static void main(String[] args) {
        ProcessEmployees pe = new ProcessEmployees();
        pe.averageSalaries();
    }
}


















