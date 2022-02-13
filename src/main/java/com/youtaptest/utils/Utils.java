package com.youtaptest.utils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Utils {

    public static <K,V,Q extends K> List<V> transform(
            final List<Q> input,
            final Function<K,V> func ) {
        if(input == null) {
            return null;
        }
        return input.stream().map(func).collect(Collectors.toList());
    }
}
