package com.zsx.jooq;

import org.jooq.*;
import org.jooq.Record;
import org.jooq.db.Tables;
import org.jooq.db.tables.records.BookRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class JooqTest {

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
}
