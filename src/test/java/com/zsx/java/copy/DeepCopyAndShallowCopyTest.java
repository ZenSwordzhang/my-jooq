package com.zsx.java.copy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class DeepCopyAndShallowCopyTest {

    @Test
    void testDeepCopy() {
        DeepCopy deepCopy = new DeepCopy();
        deepCopy.name = "ZhangSan";
        Copy copy = new Copy();
        copy.id = 1;
        deepCopy.copy = copy;

        DeepCopy cloneObj = (DeepCopy)deepCopy.clone();
        assertNotEquals(deepCopy.hashCode(), cloneObj.hashCode());
        assertEquals(deepCopy.name.hashCode(), cloneObj.name.hashCode());

        // 深拷贝，对于引用类型重新创建了一个对象
        assertNotEquals(deepCopy.copy.hashCode(), cloneObj.copy.hashCode());

        assertEquals(1, cloneObj.copy.id);
        assertEquals(deepCopy.copy.id, cloneObj.copy.id);

        copy.id = 2;
        assertEquals(1, cloneObj.copy.id);
        assertEquals(2, deepCopy.copy.id);
    }

    @Test
    void testShallowCopy() {
        ShallowCopy shallowCopy = new ShallowCopy();
        shallowCopy.name = "ZhangSan";
        Copy copy = new Copy();
        copy.id = 1;
        shallowCopy.copy = copy;

        ShallowCopy cloneObj = (ShallowCopy)shallowCopy.clone();
        assertNotEquals(shallowCopy.hashCode(), cloneObj.hashCode());
        assertEquals(shallowCopy.name.hashCode(), cloneObj.name.hashCode());

        // 浅拷贝，对于引用类型指向同一个地址
        assertEquals(shallowCopy.copy.hashCode(), cloneObj.copy.hashCode());

        assertEquals(1, cloneObj.copy.id);
        assertEquals(shallowCopy.copy.id, cloneObj.copy.id);

        copy.id = 2;
        assertEquals(2, cloneObj.copy.id);
        assertEquals(shallowCopy.copy.id, cloneObj.copy.id);
    }
}
