package com.madhusudhan.j8.streams.commonops;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.Collectors.toMap;

import com.madhusudhan.j8.domain.Movie;
import com.madhusudhan.j8.domain.Trade;
import com.madhusudhan.j8.util.MovieUtil;
import com.madhusudhan.j8.util.TradeUtil;
// Reducing Operation
public class Reducing {
	List<Trade> trades = TradeUtil.createTrades();
	List<Integer> ints = Arrays.asList();

	private void tradeInstrumentsList() {
		Optional<String> instList = trades.stream()
			.map(Trade::getInstrument)
		.reduce((a,b)-> a+","+b);
		
		System.out.println(instList);
	}
	
	private void schoolStaff() {
		List<Integer> ints = Arrays.asList(11, 13, 12, 15);
		int staff = ints.stream().reduce(10, (i,j) -> i+j);
		System.out.println("Total staff: "+staff);
		
	}

	private void tradeQuantitySum() {
		Optional<Integer> totalQuantity = 
				trades.stream()
					  .map(Trade::getQuantity)
					  .reduce((a,b) -> a+b);
			System.out.println("Total quantity: "+totalQuantity.get());
	}
	
	public static void main(String[] args) {
		new Reducing().schoolStaff();
		new Reducing().tradeQuantitySum();
		new Reducing().tradeInstrumentsList();
	}

}
