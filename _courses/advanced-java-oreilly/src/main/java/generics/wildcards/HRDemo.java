package generics.wildcards;

import java.util.Arrays;
import java.util.List;

public class HRDemo {
    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
                new Employee("Bart"), new Employee("Homer"),
                new Employee("Lisa"), new Employee("Maggie"));

        List<Salaried> salarieds = Arrays.asList(
                new Salaried("Kyle"), new Salaried("Stan"),
                new Salaried("Kenny"), new Salaried("Cartman"));

//        System.out.println(employees);
//        System.out.println(salarieds);

        // without wildcards
//        HR.printEmpNames(employees);
//        HR.printEmpNames(salarieds); // doesn't compile

        // with wildcards
//        HR.printEmpAndSubclassNames(employees);
//        System.out.println();
//        HR.printEmpAndSubclassNames(salarieds);

        HR.printAllFiltered(employees,
                e -> e.getName().length() % 2 == 0);
        System.out.println();
        HR.printAllFiltered(salarieds,
                e -> e.getName().length() % 2 == 0);

        // PECS --> produces uses extends, consumes uses super
    }
}
