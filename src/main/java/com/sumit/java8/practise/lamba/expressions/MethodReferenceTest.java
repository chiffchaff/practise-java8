package com.sumit.java8.practise.lamba.expressions;

/**
 * Created by sumijaiswal on 10/20/16.
 * src:
 * http://www.devx.com/Java/functional-features-in-java-8.html?utm_source=feedburner&utm_medium=feed&utm_campaign=Feed%3A+DevxLatestJavaArticles+%28DevX%3A+Latest+Java+Articles%29&utm_content=FeedBurner
 */
public class MethodReferenceTest {
    static Boolean checkCoolness(String s){
        return s.toLowerCase().contains("k") && !s.toLowerCase().contains("c");
    }

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

        CoolPredicate p = (x) -> MethodReferenceTest.checkCoolness(x);
        // Using method reference
        p = MethodReferenceTest::checkCoolness;
        String coolName = findCool(names,p);
        System.out.println(coolName);
    }


}

