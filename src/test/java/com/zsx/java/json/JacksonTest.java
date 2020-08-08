package com.zsx.java.json;

import com.zsx.entity.Author;
import org.junit.jupiter.api.Test;

import java.util.List;

public class JacksonTest {

    @Test
    void testJsonStringToObject() {
        String jsonString = "["
                + "{\"name\":\"zsx\",\"age\":20,\"gender\":true,\"remarks\":\"1\"}"
                + "{\"name\":\"lisisi\",\"age\":18,\"gender\":false,\"remarks\":\"2\"}"
                + "{\"name\":\"wangzhaojun\",\"age\":18,\"gender\":false,\"remarks\":\"3\"}"
                + "]";
//        List<Author> list = JSONArray.toList(JSONArray.fromObject(jsonString, Author.class);

    }
}
