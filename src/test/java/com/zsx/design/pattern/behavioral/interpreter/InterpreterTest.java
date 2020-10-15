package com.zsx.design.pattern.behavioral.interpreter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InterpreterTest {

    @Test
    void testInterpret() {
        Expression isMale = Context.getMaleExpression("ZhangSan", "LiSi");

        Expression isMarriedWoman = Context.getMarriedWomanExpression("LiSiSi", "Married");

        assertTrue(isMale.interpret("ZhangSan"));
        assertTrue(isMale.interpret("LiSi"));
        assertFalse(isMale.interpret("WangWu"));

        assertTrue(isMarriedWoman.interpret("Married LiSiSi"));
        assertFalse(isMarriedWoman.interpret("Married ZhaoLiu"));
    }
}
