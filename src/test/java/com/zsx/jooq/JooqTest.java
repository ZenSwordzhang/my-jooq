package com.zsx.jooq;

import com.zsx.MyJooqApplication;
import com.zsx.entity.Book;
import org.jooq.*;
import org.jooq.Record;
import org.jooq.db.Tables;
import org.jooq.db.tables.records.BookRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = MyJooqApplication.class)
@ExtendWith(SpringExtension.class)
public class JooqTest {

    @Autowired
    private DSLContext dslContext;

    @Test
    void testGetReferences() {
        List<ForeignKey<BookRecord, ?>> references = Tables.BOOK.getReferences();

        List<String> foreignKeyList = List.of("\"jooq\".\"book\".\"author_id\"");
        List<String> referencesKey = List.of("\"jooq\".\"author\".\"id\"");
        List<String> fieldList = List.of("\"jooq\".\"author\".\"id\"");
        List<String> tableList = List.of("\"jooq\".\"author\"");
        List<String> sqlConditionList = List.of("\"jooq\".\"author\".\"id\" = \"jooq\".\"book\".\"author_id\"");
        List<String> primaryKeyList = List.of("constraint \"author_pk\"\n  primary key (\"id\")");
        List<String> joinSqlList = List.of("\"jooq\".\"book\"\n  join \"jooq\".\"author\"\n" +
                "    on (\"jooq\".\"author\".\"id\" = \"jooq\".\"book\".\"author_id\")");

        List<String> tableNameList = List.of("author");
        List<String> pkNameList = List.of("author_pk");


        references.forEach(foreignKey-> {
            // "jooq"."book"."author_id"
            assertTrue(foreignKeyList.contains(foreignKey.getFields().get(0).toString()));

            // "jooq"."author"."id"
            assertTrue(referencesKey.contains(foreignKey.getKey().getFields().get(0).toString()));
            assertTrue(referencesKey.contains(foreignKey.getKey().getFieldsArray()[0].toString()));

            // "jooq"."author"
            Table<?> table = foreignKey.getKey().getTable();
            assertTrue(tableList.contains(table.toString()));


            // author_pk
            assertTrue(pkNameList.contains(foreignKey.getKey().getName()));

            // author
            assertTrue(tableNameList.contains(table.getName()));

            // "jooq"."author"."id"
            Field<?> field = foreignKey.getKey().getTable().field(0);
            assertTrue(fieldList.contains(field.toString()));

            UniqueKey<?> primaryKey = foreignKey.getKey().getTable().getPrimaryKey();
            assertTrue(primaryKeyList.contains(primaryKey.toString()));

            // 获取主表外键关联字段属性("jooq"."book"."author_id")
            TableField<?, ?> tableField = foreignKey.getFields().get(0);

            // 获取关联表的主键("jooq"."author"."id")
            Field<?> foreignField = primaryKey.getFields().get(0);

            String sql = String.format("%s = %s", foreignField, tableField);
            // "jooq"."author"."id" = "jooq"."book"."author_id"
            assertTrue(sqlConditionList.contains(sql));

            TableOnConditionStep<Record> step = Tables.BOOK.join(foreignKey.getKey().getTable()).on(sql);
            assertTrue(joinSqlList.contains(step.toString()));

        });
    }

    @Test
    @Rollback
    @Transactional
    void testCRUD() {
        String name = "ZhangSan";
        String title = "ZhangSan Drifting";

        var map = new HashMap<>();
        map.put("name", name);
        map.put("title", title);

        Condition condition = Tables.BOOK.NAME.eq(name);

        // 为了不影响测试，测试前删除满足条件的数据
        dslContext.delete(Tables.BOOK).where(condition).execute();

        /** =========SAVE========= */
        dslContext.insertInto(Tables.BOOK).set(map).execute();


        /** =========SELECT========= */
        int existsNum = dslContext.selectFrom(Tables.BOOK).where(condition).execute();
        assertNotEquals(0, existsNum);

        SelectQuery<BookRecord> bookRecords = dslContext.selectQuery(Tables.BOOK);
        bookRecords.addConditions(condition);
        int existsNum1 = bookRecords.execute();
        assertNotEquals(0, existsNum1);

        String selectSql = String.format("SELECT name, title FROM jooq.book Where name = '%s'", name);
        List<Book> books = dslContext.fetch(selectSql).into(Book.class);
        assert books != null;
        Book book = books.get(0);
        assert book != null;
        assertEquals(book.getName(), name);
        assertEquals(book.getTitle(), title);

        Book book1 = dslContext.fetchOne(selectSql).into(Book.class);
        assert book1 != null;
        assertEquals(book1.getName(), name);
        assertEquals(book1.getTitle(), title);

        Book book2 = dslContext.fetchOne(Tables.BOOK, condition).into(Book.class);
        assert book2 != null;
        assertEquals(book2.getName(), name);
        assertEquals(book2.getTitle(), title);

        List<Book> books1 = dslContext.fetch(Tables.BOOK, condition).into(Book.class);
        assert books1 != null;
        Book book3 = books1.get(0);
        assert book3 != null;
        assertEquals(book3.getName(), name);
        assertEquals(book3.getTitle(), title);

        /** =========UPDATE========= */
        String title1 = "ZhangSan Drifting 1";
        int updateNum = dslContext.update(Tables.BOOK).set(Tables.BOOK.TITLE, title1).where(condition).execute();
        assertNotEquals(0, updateNum);

        Book book4 = dslContext.fetchOne(selectSql).into(Book.class);
        assert book4 != null;
        assertEquals(book4.getName(), name);
        assertEquals(book4.getTitle(), title1);

        /** =========DELETE========= */

        int deleteNum = dslContext.delete(Tables.BOOK).where(condition).execute();
        assertNotEquals(0, deleteNum);

        Record record = dslContext.fetchOne(selectSql);
        assertNull(record);
    }
}
