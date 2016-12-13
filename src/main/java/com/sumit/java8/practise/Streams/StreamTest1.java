package com.sumit.java8.practise.Streams;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;

/**
 * Created by sumijaiswal on 10/26/16.
 */
public class StreamTest1 {
    public static void main(String args[]){
        Stream<String> words = Stream.of("Java", "Magazine", "is",
                "the", "best");

        Map<String, Long> letterToCount =
                words.map(w -> w.split(""))
                        .flatMap(Arrays::stream)
                        .collect(groupingBy(identity(), counting()));

        System.out.println(letterToCount);

        String headers_keys = "FCGI_ROLE|UNIQUE_ID|SCRIPT_URL|SCRIPT_URI|V_MATCH_APP|LD_LIBRARY_PATH|WEBSCR_PIN|LANG|COVFILE|COVERR|LD_PRELOAD|FAKETIME_PATH|HTTPS|HTTP_HOST|HTTP_USER_AGENT|HTTP_ACCEPT|HTTP_ACCEPT_LANGUAGE|HTTP_ACCEPT_ENCODING|HTTP_REFERER|HTTP_COOKIE|HTTP_CONNECTION|CONTENT_TYPE|CONTENT_LENGTH|HTTP_X_CLIENT_IP|PATH|SERVER_SIGNATURE|SERVER_SOFTWARE|SERVER_NAME|SERVER_ADDR|SERVER_PORT|REMOTE_ADDR|DOCUMENT_ROOT|SERVER_ADMIN|SCRIPT_FILENAME|REMOTE_PORT|GATEWAY_INTERFACE|SERVER_PROTOCOL|REQUEST_METHOD|QUERY_STRING|REQUEST_URI|SCRIPT_NAME";
        Arrays.asList(headers_keys.split("\\|")).stream().forEach(System.out::print);

        System.out.println();

        Arrays.asList(headers_keys.split("\\|")).stream().unordered().forEach(System.out::print);
        System.out.println();
        Map<String, String> kvs = Arrays.asList(headers_keys.split("\\|")).stream().collect(Collectors.toMap(e ->e, e->""));
        System.out.println(kvs.toString());
    }
}
