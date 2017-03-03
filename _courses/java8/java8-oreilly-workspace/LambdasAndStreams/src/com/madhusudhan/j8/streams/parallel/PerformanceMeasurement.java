package com.madhusudhan.j8.streams.parallel;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import com.madhusudhan.j8.domain.Trade;
import com.madhusudhan.j8.util.TradeUtil;

//Measurement of parallel operations 
public class PerformanceMeasurement {

	public void sumTradesInSerial(){
		List<Trade> trades = TradeUtil.createTrades(1000000);
		Instant start = Instant.now();
		// Sequential mode
		trades.stream()
		.map(Trade::getQuantity)
		.reduce(Integer::sum);
		
		Instant end = Instant.now();
		Duration d = Duration.between(start, end);
		System.out.println("sequential:" + d.toMillis() + "ms");
	}
	
	public void sumTradesInParallell(){
		List<Trade> trades = TradeUtil.createTrades(1000000);
		Instant start = Instant.now();
		// Parallel code
		// Sequential mode
				trades.stream()
				.parallel()
				.map(Trade::getQuantity)
				.reduce(Integer::sum);
		
		Instant end = Instant.now();
		Duration d = Duration.between(start, end);
		System.out.println("parallel:" + d.toMillis() + "ms");
	}

	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new PerformanceMeasurement().sumTradesInSerial();
		new PerformanceMeasurement().sumTradesInParallell();
	}

}
