package com.zsx.design.pattern.behavioral.template;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TemplateTest {

    @Test
    void testPlay() {
        Game lol = new LOL();
        assertEquals("LOL", lol.play());

        Game zhengTu = new ZhengTu();
        assertEquals("ZhengTu", zhengTu.play());

    }
}
