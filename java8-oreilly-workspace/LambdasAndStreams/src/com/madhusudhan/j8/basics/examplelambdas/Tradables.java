package com.madhusudhan.j8.basics.examplelambdas;

import com.madhusudhan.j8.domain.Trade;

interface ITradable<Trade>{
	boolean check(Trade t);
}

public class Tradables {
	public static void main(String[] args) {
		Tradables client = new Tradables();
		
		ITradable<Trade> bigTrade = ( Trade t ) -> t.getQuantity() > 1000000? true:false;
		
		ITradable<Trade> openTrade = (trade) -> trade.getStatus().equals("OPEN")? true:false;
	}
}
