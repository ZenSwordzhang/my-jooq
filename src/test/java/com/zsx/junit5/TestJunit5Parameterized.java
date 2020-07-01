package com.zsx.junit5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Parametric test class of junit5")
public class TestJunit5Parameterized {

    boolean isPalindrome(String word) {
        return word.toLowerCase().equals(new StringBuffer(word.toLowerCase()).reverse().toString());
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 4, 6, 8, 10})
    void testNumberShouldBeEven(int num) {
        assertEquals(0, num % 2);
    }

    @ParameterizedTest
    @ValueSource(doubles = {2.D, 4.D, 6.D, 8.D, 10.D})
    void testDoubleNumberBeEven(double num) {
        assertEquals(0, num % 2);
    }

    @ParameterizedTest
    @ValueSource(longs = {2L, 4L, 6L, 8L, 10L})
    void testLongNumberBeEven(double num) {
        assertEquals(0, num % 2);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Radar", "Rotor", "Tenet", "Madam", "AABaa"})
    void testStringShouldBePalindrome(String word) {
        assertTrue(isPalindrome(word));
    }

    @ParameterizedTest
    @CsvSource({"1,true", "2,true"})
    void testParameterized(Integer num, Boolean flag) {
        assertTrue(num < 100);
        assertTrue(flag);
    }
}
