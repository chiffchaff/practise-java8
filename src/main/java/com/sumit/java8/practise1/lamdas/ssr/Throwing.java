package com.sumit.java8.practise1.lamdas.ssr;

public interface Throwing {

    @FunctionalInterface
    interface Function<T, R, E extends Exception> {
        R apply(final T t) throws E;
    }

}

