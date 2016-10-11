package com.madhusudhan.j8.streams.advanced;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.madhusudhan.j8.domain.Trade;
import com.madhusudhan.j8.util.TradeUtil;

//Custom collectors
public class CustomCollectors {
	List<Trade> trades = TradeUtil.createTrades();
	private void collectIntoToCustomCollector() {
		Supplier<StringBuilder> supplier = StringBuilder::new;
		
		BiConsumer<StringBuilder, Trade> accumulator = 
				(sb, trade) -> sb.append(trade.getInstrument()).append(",");
				
		BiConsumer<StringBuilder, StringBuilder> combiner =
				(s1, s2) -> s1.append(s2.toString());
		
		StringBuilder results = trades.stream().collect(supplier, accumulator, combiner);
		
		System.out.println(results);
		
  	}

	private void stats() {
		List<Trade> trades = TradeUtil.createTrades();
		IntSummaryStatistics s = trades.stream().collect(Collectors.summarizingInt(Trade::getQuantity));
		System.out.println(s);
	}
	
	public static void main(String[] args) {
		new CustomCollectors().collectIntoToCustomCollector();
		new CustomCollectors().stats();
	}

}
