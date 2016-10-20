package com.sumit.java8.practise.lamba.expressions;

/**
 * Created by sumijaiswal on 10/20/16.
 * src:
 * http://www.devx.com/Java/functional-features-in-java-8.html?utm_source=feedburner&utm_medium=feed&utm_campaign=Feed%3A+DevxLatestJavaArticles+%28DevX%3A+Latest+Java+Articles%29&utm_content=FeedBurner
 */

@FunctionalInterface
interface CoolPredicate{
    boolean isCool(String candidate);
}

public class FunctionalInterfacesTest {
    static String findCool(String [] names, CoolPredicate predicate){
        for(String name:names){
            if(predicate.isCool(name)){
                return name;
            }
        }
        return  null;
    }

    public static void main(String args[]){
        String []names = {"playa", "kitCat" ,"KoolKat" ,"dog"};
        CoolPredicate p = (x) -> x.toLowerCase().contains("k") && !x.toLowerCase().contains("c");
        String coolName = findCool(names,p);
        System.out.println(coolName);
    }
}
