package com.zsx.java.json;

import com.alibaba.fastjson.JSON;
import com.zsx.entity.Author;
import net.sf.json.JSONArray;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FastJsonTest {

    @Test
    void testListToJsonString() {
        Author author1 = Author.of("zsx", 20, true, "1");
        Author author2 = Author.of("lisisi", 18, false, "2");
        Author author3 = Author.of("wangzhaojun", 18, false, "3");
        List<Author> authors = List.of(author1, author2, author3);
        String authorStr = JSON.toJSONString(authors);
        assertTrue(authorStr.contains("zsx"));
        assertTrue(authorStr.contains("lisisi"));
        assertTrue(authorStr.contains("wangzhaojun"));
    }

    @Test
    void testJsonStringToObject() {
        String authorsStr = "["
                + "{\"name\":\"zsx\",\"age\":20,\"gender\":true,\"remarks\":\"1\"},"
                + "{\"name\":\"lisisi\",\"age\":18,\"gender\":false,\"remarks\":\"2\"},"
                + "{\"name\":\"wangzhaojun\",\"age\":18,\"gender\":false,\"remarks\":\"3\"}"
                + "]";
        var authors = JSON.parseArray(authorsStr, Author.class);
        assertNotNull(authors);
        assertEquals(3, authors.size());
    }
}
