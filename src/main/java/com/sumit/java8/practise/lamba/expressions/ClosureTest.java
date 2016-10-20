package com.sumit.java8.practise.lamba.expressions;

import java.util.Arrays;

/**
 * Created by sumijaiswal on 10/20/16.
 */
public class ClosureTest {
    public static void main(String args[]){
        /**
         * Java 8 has limited support for closures,
         * which means Lambda expressions can capture variables from their enclosing scope as long as
         * those are effectively final (never modified).
         * Here is an example:
         */
        String []names = {"playa", "kitCat" ,"KoolKat" ,"dog"};
        String suffix =" is a cool name";
        /**
         * the Lambda expression uses the suffix variable declared in the enclosing scope.
         */

        String [] out = Arrays.asList(names)
                .stream()
                .filter(s -> s.toLowerCase().contains("k")&&s.toLowerCase().contains("c"))
                .map(s->s+suffix)
                .toArray(String[]::new);
        System.out.println(Arrays.asList(out));
    }
}
