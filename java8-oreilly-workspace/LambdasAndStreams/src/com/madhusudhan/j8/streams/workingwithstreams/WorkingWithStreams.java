package com.madhusudhan.j8.streams.workingwithstreams;
import java.util.List;

import com.madhusudhan.j8.domain.Movie;
import com.madhusudhan.j8.util.MovieUtil;

//Working with Streams
public class WorkingWithStreams {

	List<Movie> movies = MovieUtil.createMovies();
	
	private void findMoviesByDirectorWorking(String director) {
		movies.stream()
			.filter(m -> m.getDirector().equals(director))
			.map(Movie::getName)
//			.limit(3)
			.forEach(System.out::println);
	}

	private void findMoviesByDirector(String director) {
		movies.stream()
		.filter(m-> m.getDirector().equals("Steven Spielberg")? true:false)
		.map(Movie::getName)
		.distinct()
		.limit(3)
		.forEach(System.out::println);
	}

	public static void main(String[] args) {
		new WorkingWithStreams().findMoviesByDirectorWorking("Steven Spielberg");
	}

}
