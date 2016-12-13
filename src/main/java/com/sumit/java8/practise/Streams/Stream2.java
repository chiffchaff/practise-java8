package com.sumit.java8.practise.Streams;

import javax.sound.midi.SysexMessage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Created by sumijaiswal on 10/29/16.
 * https://www.sitepoint.com/java-8-streams-filter-map-reduce/
 */
public class Stream2 {
    public static void main(String args[]){
        // Creating a stream
        // Create an ArrayList
        List<Integer> myList = new ArrayList<Integer>();
        myList.add(1);
        myList.add(5);
        myList.add(8);

// Convert it into a Stream
        Stream<Integer> myStream = myList.stream();

        // Create an array
        Integer[] myArray = {1, 5, 8};
        // Convert it into a Stream
        Stream<Integer> myStream1 = Arrays.stream(myArray);

        /**
         * Map -> To transform one stream into another Stream object
         */
        String[] myArray1 = new String[]{"bob", "alice", "paul", "ellie"};
        Stream<String> myStream2 = Arrays.stream(myArray1);
        //you call the map method, passing a lambda expression, one which can convert a string to uppercase, as its sole argument:
        Stream<String> myNewStream2 =
                myStream2.map(s -> s.toUpperCase());

        //The Stream object returned contains the changed strings. To convert it into an array, you use its toArray method:
        String[] myNewArray =
                myNewStream2.toArray(String[]::new);


        /**
         * In the previous section, you saw that the map method processes every single element in a Stream object. You might not always want that. Sometimes, you might want to work with only a subset of the elements. To do so, you can use the filter method.

         Just like the map method, the filter method expects a lambda expression as its argument. However, the lambda expression passed to it must always return a boolean value, which determines whether or not the processed element should belong to the resulting Stream object.
         */
        Arrays.stream(myArray1)
                .filter(s -> s.length() > 4)
                .toArray(String[]::new);

        /**
         * Reduction Operations
         * if you want to find the sum of an array of integers, you can use the following code:
         */
        int myArray2[] = { 1, 5, 8 };
        int sum = Arrays.stream(myArray2).sum();
        Arrays.stream(myArray2).forEach(s->System.out.print(s+" "));
        System.out.println("Sum:"+sum);


        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        List<Integer> twoEvenSquares =
                numbers.stream()
                        .filter(n -> {
                            System.out.println("filtering " + n);
                            return n % 2 == 0;
                        })
                        .map(n -> {
                            System.out.println("mapping " + n);
                            return n * n;
                        })
                        .limit(2)
                        .collect(toList());
        System.out.println("List of Even Square Nos:"+twoEvenSquares);

    }
}
