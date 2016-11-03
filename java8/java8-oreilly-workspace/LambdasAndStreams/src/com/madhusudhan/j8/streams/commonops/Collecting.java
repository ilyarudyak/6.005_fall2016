package com.madhusudhan.j8.streams.commonops;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.Collectors.toMap;

import com.madhusudhan.j8.domain.Movie;
import com.madhusudhan.j8.domain.Trade;
import com.madhusudhan.j8.util.MovieUtil;
import com.madhusudhan.j8.util.TradeUtil;

// Collecting operations
public class Collecting {
	List<Trade> trades = TradeUtil.createTrades();
	
	private void collectIntoAList() {
		List<Trade> tradeCollection = 
				trades.stream().collect(toList());
		
		System.out.println(tradeCollection);
	}
	private void collectIntoASet() {
		Set<Trade> tradeCollection = 
				trades.stream().collect(toSet());
		
		System.out.println(tradeCollection);
	}
	private void collectIntoToAMap() {
		List<Movie> movies = MovieUtil.createMovies();
		
		Map<String, Movie> movieMap = 
				movies.stream().collect(toMap(Movie::getName, movie -> movie));
		System.out.println(movieMap);
	}
	
	public static void main(String[] args) {
		new Collecting().collectIntoAList();
		new Collecting().collectIntoASet();
		new Collecting().collectIntoToAMap();
	}

}
