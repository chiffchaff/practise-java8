package com.sumit.java8.practise.lamba.expressions;

import java.util.Arrays;

/**
 * Created by sumijaiswal on 10/20/16.
 * src:
 * http://www.devx.com/Java/functional-features-in-java-8.html?utm_source=feedburner&utm_medium=feed&utm_campaign=Feed%3A+DevxLatestJavaArticles+%28DevX%3A+Latest+Java+Articles%29&utm_content=FeedBurner
 *
 */
public class StreamsTest {
    /**
     * Streams add lazy evaluation to Java and take full advantages of Lambda expressions.
     * It is conceptually very similar to the awesome .NET LINQ (Language integrated query).
     * Here is an example, where a list of names is filtered for coolness
     * and then the phrase " is a cool name" is added to each one
     * @param args
     */
    public static void main(String args[]){
        String []names = {"playa", "kitCat" ,"KoolKat" ,"dog"};
        String [] out = Arrays.asList(names)
                .stream()
                .filter(s -> s.toLowerCase().contains("k") && !s.toLowerCase().contains("c"))
                .map(s -> s +" is a cool name")
                .toArray(String[]::new);
        System.out.println(Arrays.asList(out));
    }
}
