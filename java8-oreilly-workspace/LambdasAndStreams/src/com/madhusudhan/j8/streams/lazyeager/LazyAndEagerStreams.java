package com.madhusudhan.j8.streams.lazyeager;

import java.util.List;
import java.util.stream.Stream;

import com.madhusudhan.j8.domain.Movie;
import com.madhusudhan.j8.util.MovieUtil;

// Lazy and eager operations
public class LazyAndEagerStreams {
	List<Movie> movies = MovieUtil.createMovies();

	private void testLazy() {
		movies.stream().filter(m -> {
			System.out.println("Inside lazy operation");
			return m.isClassic() ? true : false;
		}).count();
	}

	private void testEager() {
		Stream<Movie> moviesStream = 
			movies.stream()
				.filter(m -> {
			System.out.println("Fitering");
			return m.isClassic() ? true : false;
		}).map(s -> {
			System.out.println("Mapping");
			return s;
		});

		System.out.println("" + moviesStream.count());
	}

	public static void main(String[] args) {
		new LazyAndEagerStreams().testLazy();
		new LazyAndEagerStreams().testEager();
	}

}
