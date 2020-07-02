package com.zsx.utils;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.core.GetSourceResponse;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)  // Set method execution order
public class ESUtilsTest {

    final static List<String> indexNames;

    static {
        indexNames = List.of("posts", "twitter1", "twitter2", "twitter3", "twitter4");
    }

    @Autowired
    private ESUtils esUtils;

    private void assertions(IndexResponse response) {
        assertNotNull(response);
        assertEquals(1, response.getShardInfo().getSuccessful());
        assertTrue(List.of(200, 201).contains(response.status().getStatus()));
    }

    @Order(1)
    @Test
    void testIndexExists() {
        indexNames.forEach(indexName-> {
            if (esUtils.indexExists(indexName)) {
                esUtils.deleteIndexByIndexName(indexName);
                assertFalse(esUtils.indexExists(indexName));
            }
        });
    }

    @Order(3)
    @Test
    void testDeleteIndexByIndexName() {
        String indexName = indexNames.get(0);
        if (esUtils.indexExists(indexName)) {
            AcknowledgedResponse response = esUtils.deleteIndexByIndexName(indexName);
            assertNotNull(response);
            assertTrue(response.isAcknowledged());
            assertFalse(esUtils.indexExists(indexName));
        }
    }

    @Order(5)
    @Test
    void testCreateIndexMappingSource() {
        String jsonSource = "{\n" +
                "  \"properties\": {\n" +
                "    \"message\": {\n" +
                "      \"type\": \"text\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
        CreateIndexResponse response = esUtils.createIndex(indexNames.get(1), 3, 1, jsonSource, "twitter1_alias");
        assertNotNull(response);
        assertTrue(response.isAcknowledged());
    }

    @Order(6)
    @Test
    void testCreateIndexMap() {
        Map<String, Object> message = new HashMap<>();
        message.put("type", "text");
        Map<String, Object> properties = new HashMap<>();
        properties.put("message", message);
        Map<String, Object> mapping = new HashMap<>();
        mapping.put("properties", properties);
        CreateIndexResponse response = esUtils.createIndex(indexNames.get(2), 3, 1, mapping, "twitter2_alias");
        assertNotNull(response);
        assertTrue(response.isAcknowledged());
    }

    @Order(7)
    @Test
    void testCreateIndexBuilder() {
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder();
            builder.startObject();
            {
                builder.startObject("properties");
                {
                    builder.startObject("message");
                    {
                        builder.field("type", "text");
                    }
                    builder.endObject();
                }
                builder.endObject();
            }
            builder.endObject();
            CreateIndexResponse response = esUtils.createIndex(indexNames.get(3), 3, 1, builder, "twitter3_alias");
            assertNotNull(response);
            assertTrue(response.isAcknowledged());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Order(8)
    @Test
    void testCreateIndexSource() {
        String jsonSource = "{\n" +
                "    \"settings\" : {\n" +
                "        \"number_of_shards\" : 1,\n" +
                "        \"number_of_replicas\" : 0\n" +
                "    },\n" +
                "    \"mappings\" : {\n" +
                "        \"properties\" : {\n" +
                "            \"message\" : { \"type\" : \"text\" }\n" +
                "        }\n" +
                "    },\n" +
                "    \"aliases\" : {\n" +
                "        \"twitter4_alias\" : {}\n" +
                "    }\n" +
                "}";
        CreateIndexResponse response = esUtils.createIndex(indexNames.get(4), jsonSource);
        assertNotNull(response);
        assertTrue(response.isAcknowledged());
    }

    @Order(10)
    @Test
    void testSaveIndexString() {
        String jsonString = "{" +
                "\"user\":\"zhangsan\"," +
                "\"postDate\":\"2020-06-30\"," +
                "\"message\":\"trying out Elasticsearch\"," +
                "\"number\":1" +
                "}";
        IndexResponse response = esUtils.saveIndexData(indexNames.get(0), "1", jsonString);
        assertions(response);
    }

    @Order(11)
    @Test
    void testSaveIndexMap() {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("user", "lisi");
        jsonMap.put("postDate", new Date());
        jsonMap.put("message", "trying out Elasticsearch");
        IndexResponse response = esUtils.saveIndexData(indexNames.get(0), "2", jsonMap);
        assertions(response);
    }

    @Order(12)
    @Test
    void testSaveIndexBuilder() {
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
            IndexResponse response = esUtils.saveIndexData(indexNames.get(0), "3", builder);
            assertions(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Order(13)
    @Test
    void testSaveIndexJSONObject() {
        JSONObject obj = new JSONObject();
        obj.put("user", "zhaoliu");
        obj.put("postDate", new Date());
        obj.put("message", "trying out Elasticsearch");
        IndexResponse response = esUtils.saveIndexData(indexNames.get(0), "4", obj);
        assertions(response);
    }

    @Order(20)
    @Test
    void testGetIndexDataById() {
        GetResponse getResponse = esUtils.getIndexDataById(indexNames.get(0), "1", new String[]{"message", "*Date"}, Strings.EMPTY_ARRAY, "message");
        assertNotNull(getResponse);
        String message = getResponse.getSource().get("message").toString();
        System.out.println(message);
        assertNotNull(message);
    }

    @Order(21)
    @Test
    void testGetIndexSourceDataById() {
        GetSourceResponse getSourceResponse = esUtils.getIndexSourceDataById(indexNames.get(0), "1", new String[]{"message", "*Date"}, Strings.EMPTY_ARRAY);
        assertNotNull(getSourceResponse);
        String message = getSourceResponse.getSource().get("message").toString();
        System.out.println(message);
        assertNotNull(message);
    }

    @Order(22)
    @Test
    void testAsyncGetIndexDataById() {
        GetResponse getResponse = esUtils.asyncGetIndexDataById(indexNames.get(0), "1", new String[]{"message", "*Date"}, Strings.EMPTY_ARRAY, "message");
        assertNotNull(getResponse);
        String message = getResponse.getSource().get("message").toString();
        System.out.println(message);
        assertNotNull(message);
    }

    @Order(23)
    @Test
    void testAsyncGetIndexSourceDataById() {
        GetSourceResponse getSourceResponse = esUtils.asyncGetIndexSourceDataById(indexNames.get(0), "1", new String[]{"message", "*Date"}, Strings.EMPTY_ARRAY);
        assertNotNull(getSourceResponse);
        String message = getSourceResponse.getSource().get("message").toString();
        System.out.println(message);
        assertNotNull(message);
    }

    @Order(31)
    @Test
    void testUpdateDataByIdAndScript() {
        Map<String, Object> parameters = Collections.singletonMap("count", 4);
        Script inline = new Script(ScriptType.INLINE, "painless",
                "ctx._source.number += params.count", parameters);

        UpdateResponse updateResponse = esUtils.updateDataById(indexNames.get(0), "1", inline);

        assertNotNull(updateResponse);
        assertEquals(1, updateResponse.getShardInfo().getSuccessful());
    }

    @Order(32)
    @Test
    void testUpdateDataByIdAndJsonString() {
        String jsonString = "{" +
                "\"updated\":\"2017-01-01\"," +
                "\"reason\":\"daily update\"" +
                "}";

        UpdateResponse updateResponse = esUtils.updateDataById(indexNames.get(0), "1", jsonString);

        assertNotNull(updateResponse);
        assertEquals(1, updateResponse.getShardInfo().getSuccessful());
    }

    @Order(33)
    @Test
    void testUpdateDataByIdAndXContentBuilder() {
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder();
            builder.startObject();
            {
                builder.timeField("updated", new Date());
                builder.field("reason", "daily update");
            }
            builder.endObject();

            UpdateResponse updateResponse = esUtils.updateDataById(indexNames.get(0), "1", builder);

            assertNotNull(updateResponse);
            assertEquals(1, updateResponse.getShardInfo().getSuccessful());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Order(34)
    @Test
    void testUpdateDataByIdAndJSONObject() {
        JSONObject obj = new JSONObject();
        obj.put("updated", new Date());
        obj.put("reason", "daily update");

        UpdateResponse updateResponse = esUtils.updateDataById(indexNames.get(0), "1", obj);

        assertNotNull(updateResponse);
        assertEquals(1, updateResponse.getShardInfo().getSuccessful());
    }

    @Order(40)
    @ParameterizedTest
    @CsvSource({"posts,1,200"})
    void testDeleteDataById(String indexName, String indexId, Integer version) {
        // current version is lower to the one provided
        DeleteResponse deleteResponse = esUtils.deleteDataById(indexName, indexId, version);

        assertNotNull(deleteResponse);
        assertEquals(1, deleteResponse.getShardInfo().getSuccessful());
    }
}
