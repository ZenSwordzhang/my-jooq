package com.zsx.java.json;

import com.zsx.entity.Author;
import org.json.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrgJsonTest {

    @Test
    void testJsonObject() {
        JSONObject jsonObj = new JSONObject("{'name':'zsx','age':20}");
        String name = jsonObj.getString("name");
        assertEquals("zsx", name);
        int age = jsonObj.getInt("age");
        assertEquals(20, age);
    }

    @ParameterizedTest
    @CsvSource({"zsx,2020080601,13500001111", "lisi,2020080602,13500002222"})
    void testPrepareJSONObject(String name, Integer id, String phone) {
        JSONObject studentJSONObject = new JSONObject();
        studentJSONObject.put("name", name);
        studentJSONObject.put("id", id);
        studentJSONObject.put("phone", phone);
        System.out.println(studentJSONObject.toString());
    }

    @Test
    void testJsonArray() {
        JSONArray jsonarray = new JSONArray("[{'name':'zsx','age':20},{'name':'lisi','age':15}]");
        for (int i = 0; i < jsonarray.length(); i++) {
            JSONObject jsonObj = jsonarray.getJSONObject(i);
            String name = jsonObj.getString("name");
            int age = jsonObj.getInt("age");
            System.out.println("name = " + name + ",age = " + age);
        }
    }

    @Test
    void testJsonObjectAndArray() {
        String jsonString = "{'name':'zsx','age':20,'book':['book1','book2']}";
        JSONObject jsonObj = new JSONObject(jsonString);

        String name = jsonObj.getString("name");
        System.out.println("name" + ":" + name);

        int age = jsonObj.getInt("age");
        System.out.println("age" + ":" + age);

        JSONArray jsonarray = jsonObj.getJSONArray("book");
        for (int i = 0; i < jsonarray.length(); i++) {
            String book = jsonarray.getString(i);
            System.out.println("book" + i + ":" + book);
        }
    }

    @Test
    void testJsonStringer() {
        JSONStringer stringer = new JSONStringer();
        stringer.object().key("name").value("zsx").key("age").value(20).endObject();
        System.out.println(stringer);
    }


    @Test
    void testJsonTokener() {
        String json = "{"
                + "  \"name\": \"zsx\", "
                + "  \"book\": [ \"book1\",\"book2\" ] "
                + "}";

        // 方法1
        InputStream is = new ByteArrayInputStream(json.getBytes());
        Reader reader = new BufferedReader(new InputStreamReader(is));
        JSONObject jsonObj = new JSONObject(new JSONTokener(reader));

        // 方法2
//        JSONObject jsonObj = (JSONObject) new JSONTokener(json).nextValue();

        String name = jsonObj.getString("name");
        System.out.println("name" + ":" + name);
        JSONArray book = jsonObj.getJSONArray("book");
        System.out.println("book" + ":" + book);

    }

    @Test
    void testStringToJson() {
        //定义JSON字符串
        String jsonStr = "{\"id\": 1," +
                " \"title\": \"json title\", " +
                "\"config\": {" +
                "\"width\": 30," +
                "\"height\": 40," +
                "}, \"data\": [" +
                "\"JAVA\", \"JavaScript\", \"PHP\"" +
                "]}";

        //转换成为JSONObject对象
        JSONObject jsonObj = new JSONObject(jsonStr);

        //根据属性名称获取int型数据;
        assertEquals(1, jsonObj.getInt("id"));
        //根据属性名获取String数据;
        assertEquals("json title", jsonObj.getString("title"));

        //根据属性名获取JSONObject类
        JSONObject config = jsonObj.getJSONObject("config");
        assertEquals(30, config.getInt("width"));
        assertEquals(40, config.getInt("height"));

        //根据属性名获取JSONArray数组
        JSONArray data = jsonObj.getJSONArray("data");
        String[] expected = {"JAVA", "JavaScript", "PHP"};
        String[] actual = new String[data.length()];
        for (int index = 0, length = data.length(); index < length; index++) {
            actual[index] = data.get(index).toString();
        }
        assertArrayEquals(expected, actual);
    }

    @Test
    void testJsonToString() {
        //创建JSONObject对象
        JSONObject json = new JSONObject();

        //向json中添加数据
        json.put("username", "zsx");
        json.put("height", 115);
        json.put("age", 20);

        //创建JSONArray数组，并将json添加到数组
        JSONArray array = new JSONArray();
        array.put(json);

        //转换为字符串
        String jsonStr = array.toString();

        System.out.println(jsonStr);
    }

    @Test
    void testListToJSONArray() {
        //初始化ArrayList集合并添加数据
        List<String> list = new ArrayList<>();
        list.add("username");
        list.add("age");
        list.add("sex");

        //初始化HashMap集合并添加数组
        Map<String, Object> map = new HashMap<>();
        map.put("book", "Java从入门到放弃");
        map.put("price", 59.0);

        //初始化JSONArray对象，并添加数据
        JSONArray array = new JSONArray();
        array.put(list);
        array.put(map);
        System.out.println(array);
    }


    @Test
    void testBuildJsonString() {
        JSONObject json = new JSONObject();
        Author author1 = Author.of("zsx", 20, true, "1");
        json.put("author1", author1);
        Author author2 = Author.of("lisisi", 18, false, "2");
        json.put("author2", author2);
    }

}
