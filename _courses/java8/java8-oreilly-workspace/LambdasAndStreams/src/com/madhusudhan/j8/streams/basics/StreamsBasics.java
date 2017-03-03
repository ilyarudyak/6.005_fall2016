package com.madhusudhan.j8.streams.basics;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.madhusudhan.j8.domain.Trade;
import com.madhusudhan.j8.util.TradeUtil;

// Streams Basics PART 2
public class StreamsBasics {
	// Collecting big trades using Streams
	private void findLargeTradesUsingStreams(List<Trade> trades) {
		
		List<Trade> largeTrades = trades.parallelStream()
			.filter(trade ->trade.getQuantity() > 10000)
			.filter(Trade::isCancelledTrade)
			.limit(10)
			.collect(Collectors.toList());
		
		System.out.println();
		System.out.println("with streams");
		System.out.println(largeTrades);
	}

	private List<Trade> findLargeTradesUsingPreJava8(List<Trade> trades) {
		List<Trade> largeTrades = new ArrayList<Trade>();
		// Logic for collecting the large trades

		for (Trade trade : trades) {
			if (trade.getQuantity() > 10000
					&& trade.getStatus().equals("CANCEL"))
				largeTrades.add(trade);
		}
		
		System.out.println("without streams");
		System.out.println(largeTrades);
		
		return largeTrades;
	}

	public static void main(String[] args) {
		StreamsBasics basics = new StreamsBasics();
		List<Trade> trades = TradeUtil.createTrades();
		basics.findLargeTradesUsingPreJava8(trades);
		basics.findLargeTradesUsingStreams(trades);
		
	}

}
