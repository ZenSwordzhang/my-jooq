package com.zsx.java;

import com.google.common.collect.Lists;
import com.zsx.MyJooqApplication;
import com.zsx.entity.Book;
import org.jooq.DSLContext;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.jooq.db.Tables.BOOK;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = MyJooqApplication.class)
@ExtendWith(SpringExtension.class)
public class TransactionalTest {

    @Autowired
    private DSLContext dslContext;


    private final static List<String> list = Lists.newArrayList();

    @Order(1)
    @Test
    void test() {
        int m = dslContext.insertInto(BOOK).set(BOOK.NAME, "Java").execute();
        assertEquals(1, m);
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            throw new IllegalArgumentException("Illegal Argument Exception");
        });
        assertEquals("Illegal Argument Exception", exception.getMessage());
        List<Book> books = dslContext.fetch(BOOK).into(Book.class);
        assertNotNull(books);
        assertEquals(books.size(), 1);
    }

    @Order(2)
    @Test
    void test2() {
        int m = dslContext.insertInto(BOOK).set(BOOK.NAME, "Python").execute();
        assertEquals(1, m);
        List<Book> books = dslContext.fetch(BOOK).into(Book.class);
        assertNotNull(books);
        assertEquals(books.size(), 1);
    }
}
