package com.madhusudhan.j8.basics.examplelambdas;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.Comparator;

import com.madhusudhan.j8.domain.Trade;

// Examples using Runnable Lambda Expressions

public class ExampleLambdaExpressions_Runnables {

	private void testRunnable(Runnable runnable) {
		new Thread(runnable).start();
	}
	
		
	public static void main(String[] args) throws Exception {
		ExampleLambdaExpressions_Runnables client = new ExampleLambdaExpressions_Runnables();
	
		//1. Runnables
		
		// using a simple expression
		client.testRunnable(() -> System.out.println("Running a new Thread..using lambda!"));
		
		// using a complex code block
		client.testRunnable( () ->{
			System.out.println("Running in a new thread..using complex code block");
			persist();
			email();
		});
	
	}

	private static void email() {
		
	}

	private static void persist() {
		
	}
	
	
}
