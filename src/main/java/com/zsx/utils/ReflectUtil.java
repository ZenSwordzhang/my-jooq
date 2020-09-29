package com.zsx.utils;

import com.google.common.collect.Lists;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ReflectUtil {

    public static List<Field> getAllFields(Class<?> clazz) {
        List<Field> list = Lists.newArrayList();

        Field[] declaredFields = clazz.getDeclaredFields();
        list.addAll(Arrays.asList(declaredFields));

        Class<?> superclass = clazz.getSuperclass();
        if (superclass != null ) {
            list.addAll(getAllFields(superclass));
        }

        return list.stream().distinct().collect(Collectors.toList());
    }
}
