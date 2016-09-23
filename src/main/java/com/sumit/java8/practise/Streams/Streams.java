package com.sumit.java8.practise.Streams;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Streams {

	private enum Status {
		OPEN, CLOSED
	};

	private static final class Task {
		private final Status status;
		private final Integer points;

		Task(final Status status, final Integer points) {
			this.status = status;
			this.points = points;
		}

		public Integer getPoints() {
			return points;
		}

		public Status getStatus() {
			return status;
		}

		@Override
		public String toString() {
			return String.format("[%s, %d]", status, points);
		}
	}
	
	public static void main(String[] args) {
		final Collection< Task > tasks = Arrays.asList(
				    new Task( Status.OPEN, 5 ),
				    new Task( Status.OPEN, 13 ),
				    new Task( Status.CLOSED, 8 )
				);
		/**
		 * streams: a sequence of elements supporting sequential and parallel aggregate operations.
		 */
		// Calculate total points of all active tasks using sum()
		final long totalPointsOfOpenTasks = tasks
		    .stream()
		    .filter( task -> task.getStatus() == Status.OPEN )
		    .mapToInt( Task::getPoints )
		    .sum();
		System.out.println( "Total points: " + totalPointsOfOpenTasks );

		
		// Calculate total points of all tasks
		/**
		 * Yet another value proposition of the streams is out-of-the box support of parallel processing
		 */
		// we try to process all the tasks in parallel and calculate the final result using reduce method.
		final double totalPoints = tasks
		   .stream()
		   .parallel()
		   .map( task -> task.getPoints() ) // or map( Task::getPoints )
		   .reduce( 0, Integer::sum );
		System.out.println( "Total points (all tasks): " + totalPoints );

		
		/**
		 * Often, there is a need to performing a grouping of the collection elements by some criteria. 
		 * Streams can help with that as well as an example below demonstrates.
		 */
		// Group tasks by their status
		final Map< Status, List< Task > > map = tasks
		    .stream()
		    .collect( Collectors.groupingBy( Task::getStatus ) );
		System.out.println( map );

		
		/**
		 * let us calculate the overall percentage (or weight) of each task across the whole collection, based on its points.
		 */
		// Calculate the weight of each tasks (as percent of total points)
		final Collection< String > result = tasks
		    .stream()                                        // Stream< String >
		    .mapToInt( Task::getPoints )                     // IntStream
		    .asLongStream()                                  // LongStream
		    .mapToDouble( points -> points / totalPoints )   // DoubleStream
		    .boxed()                                         // Stream< Double >
		    .mapToLong( weigth -> ( long )( weigth * 100 ) ) // LongStream
		    .mapToObj( percentage -> percentage + "%" )      // Stream< String>
		    .collect( Collectors.toList() );                 // List< String >

		System.out.println( result );

		/**
		 * Stream API is not only about Java collections. 
		 * The typical I/O operations like reading the text file line by line is a very good candidate to benefit from stream processing
		 */
		final Path path = new File("test.properties").toPath();
		try( Stream< String > lines = Files.lines( path, StandardCharsets.UTF_8 ) ) {
		    lines.onClose( () -> System.out.println("Done!") ).map(s->s+"tseting").
		    forEach(System.out::println );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
	}
}
