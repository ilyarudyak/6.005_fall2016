package com.madhusudhan.j8.streams.parallel;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Stream;

import com.madhusudhan.j8.domain.Trade;
import com.madhusudhan.j8.util.TradeUtil;

//Parallel Streams
public class ParallelStreams {
	List<Trade> trades = TradeUtil.createTrades();

	private void serialStream() {
		Optional<Integer> sumOfTrades = trades
				.stream()
				.filter(t -> {
					System.out.printf("Id: %d Filter Thread: %s:\n", t.getId(),
							Thread.currentThread().getName());
					return t.isBigTrade();
				})
				.map(t -> {
					System.out.printf("Id: %d Map Thread: %s:\n", t.getId(),
							Thread.currentThread().getName());
					return t.getQuantity();
				}).reduce(Integer::sum);

		System.out.println(sumOfTrades.get());

	}

	private void parallelStream() {
		
		Optional<Integer> sumOfTrades = trades
				.parallelStream()
				.peek(t -> System.out.printf("id=%d thread=%s\n",t.getId(), Thread.currentThread().getName()))
				.filter(Trade::isBigTrade)
				.peek(t -> System.out.printf("id=%d filter_thread=%s\n",t.getId(), Thread.currentThread().getName()))
				.map(t -> t.getQuantity())
				.peek(t -> System.out.printf("map_thread=%s\n", Thread.currentThread().getName()))
				.reduce(Integer::sum);

		System.out.println(sumOfTrades.get());
	}

	private void collectionParallelStream() {

		Stream<Trade> parallelStreams = trades.parallelStream();

	}

	public static void main(String[] args) {
//		 new ParallelStreams().serialStream();
		new ParallelStreams().parallelStream();
	}

}












