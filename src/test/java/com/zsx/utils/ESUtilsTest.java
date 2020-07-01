package com.zsx.utils;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.core.GetSourceResponse;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ESUtilsTest {

    @Autowired
    private ESUtils esUtils;

    @Test
    void testCreateIndexString() {
        String jsonString = "{" +
                "\"user\":\"zhangsan\"," +
                "\"postDate\":\"2020-06-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";
        IndexResponse response = esUtils.saveIndexData("posts", "1", jsonString);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(200, response.status().getStatus());
    }

    @Test
    void testCreateIndexMap() {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("user", "lisi");
        jsonMap.put("postDate", new Date());
        jsonMap.put("message", "trying out Elasticsearch");
        IndexResponse response = esUtils.saveIndexData("posts", "2", jsonMap);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(200, response.status().getStatus());
    }

    @Test
    void testCreateIndexBuilder() {
        XContentBuilder builder = null;
        try {
            builder = XContentFactory.jsonBuilder();
            builder.startObject();
            {
                builder.field("user", "wangwu");
                builder.timeField("postDate", new Date());
                builder.field("message", "trying out Elasticsearch");
            }
            builder.endObject();
            IndexResponse response = esUtils.saveIndexData("posts", "3", builder);
            Assertions.assertNotNull(response);
            Assertions.assertEquals(200, response.status().getStatus());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testCreateIndexJSONObject() {
        JSONObject obj = new JSONObject();
        obj.put("user", "zhaoliu");
        obj.put("postDate", new Date());
        obj.put("message", "trying out Elasticsearch");
        IndexResponse response = esUtils.saveIndexData("posts", "4", obj);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(200, response.status().getStatus());
    }

    @Test
    void testGetIndexContentById() {
        String indexName = "posts";
        String indexId = "1";
        String[] includes = new String[]{"message", "*Date"};
        String[] excludes = Strings.EMPTY_ARRAY;
        GetResponse getResponse = esUtils.getResponseById(indexName, indexId, includes, excludes, "message");
        Assertions.assertNotNull(getResponse);
        String message = getResponse.getSource().get("message").toString();
        System.out.println(message);
        Assertions.assertNotNull(message);
    }

    @Test
    void testGetSourceRequestById() {
        String indexName = "posts";
        String indexId = "1";
        String[] includes = new String[]{"message", "*Date"};
        String[] excludes = Strings.EMPTY_ARRAY;
        GetSourceResponse getSourceResponse = esUtils.getSourceResponseById(indexName, indexId, includes, excludes);
        Assertions.assertNotNull(getSourceResponse);
        String message = getSourceResponse.getSource().get("message").toString();
        System.out.println(message);
        Assertions.assertNotNull(message);
    }

    @Test
    void testAsyncGetIndexContentById() {
        String indexName = "posts";
        String indexId = "1";
        String[] includes = new String[]{"message", "*Date"};
        String[] excludes = Strings.EMPTY_ARRAY;
        GetResponse getResponse = esUtils.asyncGetResponseById(indexName, indexId, includes, excludes, "message");
        Assertions.assertNotNull(getResponse);
        String message = getResponse.getSource().get("message").toString();
        System.out.println(message);
        Assertions.assertNotNull(message);
    }

    @Test
    void testAsyncGetSourceRequestById() {
        String indexName = "posts";
        String indexId = "1";
        String[] includes = new String[]{"message", "*Date"};
        String[] excludes = Strings.EMPTY_ARRAY;
        GetSourceResponse getSourceResponse = esUtils.asyncGetSourceResponseById(indexName, indexId, includes, excludes);
        Assertions.assertNotNull(getSourceResponse);
        String message = getSourceResponse.getSource().get("message").toString();
        System.out.println(message);
        Assertions.assertNotNull(message);
    }
}
