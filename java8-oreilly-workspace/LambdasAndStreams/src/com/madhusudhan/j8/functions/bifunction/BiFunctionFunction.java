package com.madhusudhan.j8.functions.bifunction;
import java.util.function.BiFunction;
import java.util.function.Function;

import com.madhusudhan.j8.domain.Employee;
import com.madhusudhan.j8.domain.Manager;

// TWO argument Function - BiFunction

public class BiFunctionFunction {

	BiFunction<Employee, Manager, Employee> empManagerBiFunction = (emp, manager) -> emp.getManager().getPersonalAssistant();
	
	Function<Employee, Employee> emplManagerFunction = emp -> emp.getManager().getPersonalAssistant();
	
	private void testBiFunction(Employee emp, Manager manager) {
		Employee employee = empManagerBiFunction.apply(emp, manager);
		System.out.println("Employee"+employee);
	}

	private void testAndThen(Employee emp, Manager manager) {
		BiFunction<Employee, Manager, Employee> perAssistant = empManagerBiFunction.andThen(emplManagerFunction);
		Employee pa = perAssistant.apply(emp, manager);
	}

	public static void main(String[] args) {
		Employee emp = new Employee(99);
		Manager manager = new Manager();
		emp.setManager(manager);
		manager.setPersonalAssistant(emp);
		new BiFunctionFunction().testBiFunction(emp, manager);
	}
}
