package com.zsx.java.collection;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class SynchronizedListTest {

    private static final List<String> list = Lists.newArrayList();

    private final static List<String> synList = Collections.synchronizedList(list);

    private final static List<String> synList2 = Collections.synchronizedList(Lists.newArrayList());

    @Test
    void testAddAndClear() {
        assertEquals(0, list.size());
        assertEquals(list.size(), synList.size());

        // list改变synList也同时改变
        list.add("A");
        list.add("B");
        list.add("C");
        assertTrue(synList.contains("A"));
        assertTrue(synList.contains("B"));
        assertTrue(synList.contains("C"));
        assertEquals(list.size(), synList.size());

        list.clear();
        assertEquals(0, synList.size());

        // synList改变list也同时改变
        synList.add("E");
        synList.add("F");
        synList.add("G");
        assertTrue(list.contains("E"));
        assertTrue(list.contains("F"));
        assertTrue(list.contains("G"));
        assertEquals(list.size(), synList.size());

        synList.clear();
        assertEquals(0, list.size());
        assertEquals(list.size(), synList.size());
    }


    @Test
    void testLockForList() {
        AtomicInteger m = new AtomicInteger();
        synList2.add(String.valueOf(m.getAndIncrement()));
        synList2.add(String.valueOf(m.getAndIncrement()));

        // 1.main---===[0, 1]
        System.out.println(Thread.currentThread().getName() + "---===" + synList2);

        new Thread(()-> {
            synchronized (synList2) {
                // 2.Thread-1---===[0, 1]
                System.out.println(Thread.currentThread().getName() + "---===" + synList2);
                sleep(4);
                synList2.clear();
                // 3.Thread-1+++===[]
                System.out.println(Thread.currentThread().getName() + "+++===" + synList2);
            }
        },"Thread-1").start();

        // 确保上面线程先执行
        sleep(1);

        // 4.main***===[] : Thread-1中synList2加了同步锁，其他线程不能操作synList2集合，需要等待Thread-1释放锁
        System.out.println(Thread.currentThread().getName() + "***===" + synList2);

        new Thread(()-> {
            System.out.println(Thread.currentThread().getName() + "---***" + synList2);
            synList2.add(String.valueOf(m.getAndIncrement()));
            sleep(3);
            // 6.Thread-2+++***[2]
            System.out.println(Thread.currentThread().getName() + "+++***" + synList2);
        }, "Thread-2").start();

        sleep(1);

        // 5.main+++===[2]: Thread-2中synList2没加同步锁的情况下，可以直接操作synList2集合
        System.out.println(Thread.currentThread().getName() + "+++===" + synList2);

        while (Thread.activeCount() > 2) {}
    }

    private void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
