package com.sumit.java8.practise;

import java.util.Arrays;

/**
 * Lambdas and Functional Interfaces
 * Lambdas (also known as closures) 
 * @author sumijaiswal
 *
 */
public class Practise1 {
	public static void main(String[] args) {
		Arrays.asList( "a", "b", "d" ).forEach( e -> System.out.println( e ) );
		Arrays.asList( "a", "b", "d" ).forEach( ( String e ) -> System.out.println(e));
		Arrays.asList( "a", "b", "d" ).forEach( ( String e ) ->{
			System.out.print(e);
			System.out.println(e);
		});
		
		/**
		 * Lambdas may reference the class members and local variables 
		 * (implicitly making them effectively final if they are not)
		 */
		final String separator = ",";
		Arrays.asList( "a", "b", "d" ).forEach(
		    ( String e ) -> System.out.print( e + separator ) );
		
		
		/**
		 * Lambdas may return a value. The type of the return value will be inferred by compiler. 
		 * The return statement is not required if the lambda body is just a one-liner. 
		 */
		Arrays.asList("1","2","3").sort((e,e1)->e.compareTo(e1));
		
		Arrays.asList("a","b","c").sort((e,e1)->{
			int result = e.compareTo(e1);
			return result;
		});


		int [] num = {1,4,5};
		//Arrays.sort(num, (a, b) -> Integer.signum( a ) - Integer.signum( b ));
		
	}
}
