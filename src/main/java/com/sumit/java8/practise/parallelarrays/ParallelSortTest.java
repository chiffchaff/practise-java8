package com.sumit.java8.practise.parallelarrays;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Java 8 release adds a lot of new methods to allow parallel arrays processing.
 * Arguably, the most important one is parallelSort() which may significantly
 * speedup the sorting on multicore machines. The following small example
 * demonstrates this new method family (parallelXxx) in action.
 * 
 * @author sumijaiswal
 *
 */
public class ParallelSortTest {
	/**
	 * The program outputs first 10 elements before 
	 * and after sorting so to ensure the array is really ordered.
	 * @param args
	 */
	public static void main(String[] args) {
		
		long[] arrayOfLong = new long[20000];
		//parallelSetAll() to fill up arrays with 20000 random values
		Arrays.parallelSetAll(arrayOfLong, index -> ThreadLocalRandom.current().nextInt(1000000));
		Arrays.stream(arrayOfLong).limit(10).forEach(i -> System.out.print(i + " "));
		System.out.println();

		Arrays.parallelSort(arrayOfLong);
		Arrays.stream(arrayOfLong).limit(10).forEach(i -> System.out.print(i + " "));
		System.out.println();

	}
}
