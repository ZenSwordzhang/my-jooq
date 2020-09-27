package com.zsx.java.stream;

import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

public class StreamTest {

    @Test
    void testStreamConcat() {
        Stream<String> stream1 = Stream.of("A", "B", "C", "D", "E");
        String[] arr = {"1","2","3","4","5"};
        Stream<String> stream2 = Stream.of(arr);

        //把以上两个流组合为一个流
        Stream<String> concatStream = Stream.concat(stream1, stream2);

        concatStream.forEach(System.out :: println);
    }
}
