package com.zsx.java.reflect;

import com.zsx.java.reflect.ab.TestB;
import com.zsx.java.reflect.c.TestC;
import com.zsx.utils.ReflectUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@DisplayName("反射测试类")
public class ReflectTest {

    @Test
    void testGetFields() {
        // 会获取自身和父类的public属性字段
        Field[] fields = TestB.class.getFields();
        List<String> fieldNames = Arrays.stream(fields).map(Field :: getName).collect(Collectors.toList());
        Assertions.assertLinesMatch(List.of("height1", "alias1"), fieldNames);

        Field[] fields2 = TestC.class.getFields();
        List<String> fieldNames2 = Arrays.stream(fields2).map(Field :: getName).collect(Collectors.toList());
        Assertions.assertLinesMatch(List.of("color1", "height1", "alias1"), fieldNames2);
    }

    @Test
    void testGetDeclaredFields() {
        // 只会自身的所有属性字段
        Field[] declaredFields = TestB.class.getDeclaredFields();
        List<String> fieldNames = Arrays.stream(declaredFields).map(Field :: getName).collect(Collectors.toList());
        Assertions.assertLinesMatch(List.of("height1", "height2", "height3", "height4"), fieldNames);

        Field[] declaredFields2 = TestC.class.getDeclaredFields();
        List<String> fieldNames2 = Arrays.stream(declaredFields2).map(Field :: getName).collect(Collectors.toList());
        Assertions.assertLinesMatch(List.of("color1", "color2", "color3", "color4"), fieldNames2);
    }

    @Test
    void testGetAllField() {
        List<Field> allFields = ReflectUtil.getAllFields(TestB.class);
        List<String> fieldNames = allFields.stream().map(Field :: getName).collect(Collectors.toList());
        List<String> list = List.of("height1", "height2", "height3", "height4", "alias1", "alias2", "alias3", "alias4");
        Assertions.assertLinesMatch(list, fieldNames);

        List<Field> allFields2 = ReflectUtil.getAllFields(TestC.class);
        List<String> fieldNames2 = allFields2.stream().map(Field :: getName).collect(Collectors.toList());
        List<String> list2 = List.of("color1", "color2", "color3", "color4", "height1", "height2", "height3", "height4", "alias1", "alias2", "alias3", "alias4");
        Assertions.assertLinesMatch(list2, fieldNames2);
    }
}
