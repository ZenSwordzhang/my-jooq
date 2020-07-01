package com.zsx.utils;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.ActiveShardCount;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.GetSourceRequest;
import org.elasticsearch.client.core.GetSourceResponse;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.VersionType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class ESUtils {

    @Value("${es.hosts}")
    private String[] hosts;
    @Value("${es.scheme}")
    private String scheme;

    private RestHighLevelClient restHighLevelClient;

    @PostConstruct
    private void init() {
        try {
            if (restHighLevelClient != null) {
                restHighLevelClient.close();
            }
            HttpHost[] hostArr = new HttpHost[hosts.length];
            for (int i = 0; i < hostArr.length; i++) {
                hostArr[i] = new HttpHost(hosts[i].split(":")[0], Integer.parseInt(hosts[i].split(":")[1]), scheme);
            }
            restHighLevelClient = new RestHighLevelClient(RestClient.builder(hostArr));
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

    private IndexRequest builderIndexRequest(String indexName, String indexId) {
        IndexRequest request = new IndexRequest(indexName);
        if (!StringUtils.isEmpty(indexId)) {
            request.id(indexId);
        }
        return request;
    }

    private IndexResponse getIndexResponse(IndexRequest request) {
        try {
            return restHighLevelClient.index(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return null;
        }
    }

    private GetRequest builderGetRequest(String indexName, String indexId, String[] includes, String[] excludes, String[] fields) {
        GetRequest getRequest = new GetRequest(indexName, indexId);

        // Disable source retrieval, enabled by default
        getRequest.fetchSourceContext(FetchSourceContext.DO_NOT_FETCH_SOURCE);

        FetchSourceContext fetchSourceContext =
                new FetchSourceContext(true, includes, excludes);
        getRequest.fetchSourceContext(fetchSourceContext);

        // Configure retrieval for specific stored fields (requires fields to be stored separately in the mappings)
        getRequest.storedFields(fields);
        return getRequest;
    }

    private GetSourceRequest builderGetSourceRequest(String indexName, String indexId, String[] includes, String[] excludes) {
        GetSourceRequest getSourceRequest = new GetSourceRequest(indexName, indexId);

        // FetchSourceContext 's first argument fetchSource must be true, otherwise ElasticsearchException get thrown
        getSourceRequest.fetchSourceContext(new FetchSourceContext(true, includes, excludes));

        // Optional arguments
        getSourceRequest.routing("routing");
        getSourceRequest.preference("preference");

        // Set realtime flag to false (true by default)
        getSourceRequest.realtime(false);

        // Perform a refresh before retrieving the document (false by default)
//        getSourceRequest.refresh(true);
        return getSourceRequest;
    }

    private UpdateResponse getUpdateResponse(UpdateRequest request) {
        UpdateResponse response = null;
        try {
            response = restHighLevelClient.update(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return response;
    }

    private Settings.Builder getBuilder(Integer shards, Integer replicas) {
        Settings.Builder builder = Settings.builder();
        if (shards != null) {
            builder.put("index.number_of_shards", shards);
        }
        if (replicas != null) {
            builder.put("index.number_of_replicas", replicas);
        }
        return builder;
    }

    private CreateIndexResponse getCreateIndexResponse(CreateIndexRequest request) {
        CreateIndexResponse createIndexResponse = null;
        try {
            createIndexResponse = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return createIndexResponse;
    }

    private CreateIndexRequest builderCreateIndexRequest(String indexName, Integer shards, Integer replicas) {
        CreateIndexRequest request = new CreateIndexRequest(indexName);

        Settings.Builder builder = getBuilder(shards, replicas);
        // index Settings
        request.settings(builder);
        return request;
    }

    public boolean indexExists(String indexName) {
        GetIndexRequest request = new GetIndexRequest(indexName);

        // Optional arguments
        request.local(false);
        request.humanReadable(true);
        request.includeDefaults(false);
        request.indicesOptions(IndicesOptions.strictExpand());
        try {
            return restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return false;
        }
    }

    public AcknowledgedResponse deleteIndexByIndexName(String indexName) {
        DeleteIndexRequest request = new DeleteIndexRequest(indexName);

        // Optional arguments, for the all the nodes
        request.timeout(TimeValue.timeValueMinutes(2));
//        request.timeout("2m");

        request.masterNodeTimeout(TimeValue.timeValueMinutes(1));
        request.masterNodeTimeout("1m");

        // Setting IndicesOptions controls how unavailable indices are resolved and how wildcard expressions are expanded
        request.indicesOptions(IndicesOptions.lenientExpandOpen());
        AcknowledgedResponse deleteIndexResponse = null;
        try {
            deleteIndexResponse = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return deleteIndexResponse;
    }

    public CreateIndexResponse createIndex(String indexName, Integer shards, Integer replicas, String mappingSource, String alias) {

        CreateIndexRequest request = builderCreateIndexRequest(indexName, shards, replicas);

        // index mappings
        request.mapping(mappingSource, XContentType.JSON);

        // index aliases
        request.alias(new Alias(alias).filter(QueryBuilders.termQuery("user", "kimchy")));

        // Optional arguments, for the all the nodes
        request.setTimeout(TimeValue.timeValueMinutes(2));
        request.setMasterTimeout(TimeValue.timeValueMinutes(1));

//        request.waitForActiveShards(ActiveShardCount.from(2));
        request.waitForActiveShards(ActiveShardCount.DEFAULT);

        return getCreateIndexResponse(request);
    }

    public CreateIndexResponse createIndex(String indexName, Integer shards, Integer replicas, Map<String, Object> mapping, String alias) {

        CreateIndexRequest request = builderCreateIndexRequest(indexName, shards, replicas);

        // index mappings
        request.mapping(mapping);

        // index aliases
        request.alias(new Alias(alias).filter(QueryBuilders.termQuery("user", "kimchy")));

        return getCreateIndexResponse(request);
    }

    public CreateIndexResponse createIndex(String indexName, Integer shards, Integer replicas, XContentBuilder xContentBuilder, String alias) {

        CreateIndexRequest request = builderCreateIndexRequest(indexName, shards, replicas);

        // index mappings
        request.mapping(xContentBuilder);

        // index aliases
        request.alias(new Alias(alias).filter(QueryBuilders.termQuery("user", "kimchy")));

        return getCreateIndexResponse(request);
    }

    public CreateIndexResponse createIndex(String indexName, String jsonSource) {
        CreateIndexRequest request = new CreateIndexRequest(indexName);

        request.source(jsonSource, XContentType.JSON);
        return getCreateIndexResponse(request);
    }

    public IndexResponse saveIndexData(String indexName, String indexId, String jsonSource) {
        IndexRequest request = builderIndexRequest(indexName, indexId);
        request.source(jsonSource, XContentType.JSON);
        return getIndexResponse(request);
    }

    public IndexResponse saveIndexData(String indexName, String indexId, Map<String, Object> jsonSource) {
        IndexRequest request = builderIndexRequest(indexName, indexId);
        request.source(jsonSource);
        return getIndexResponse(request);
    }

    public IndexResponse saveIndexData(String indexName, String indexId, XContentBuilder builder) {
        IndexRequest request = builderIndexRequest(indexName, indexId);
        request.source(builder);
        return getIndexResponse(request);
    }

    public IndexResponse saveIndexData(String indexName, String indexId, JSONObject obj) {
        IndexRequest request = builderIndexRequest(indexName, indexId);
        request.source(obj.toMap());
        return getIndexResponse(request);
    }

    public GetResponse getIndexDataById(String indexName, String indexId, String[] includes, String[] excludes, String... fields) {
        GetRequest getRequest = builderGetRequest(indexName, indexId, includes, excludes, fields);
        GetResponse getResponse = null;
        try {
            getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return getResponse;
    }

    public GetSourceResponse getIndexSourceDataById(String indexName, String indexId, String[] includes, String[] excludes) {
        GetSourceRequest getSourceRequest = builderGetSourceRequest(indexName, indexId, includes, excludes);

        GetSourceResponse getSourceResponse = null;
        try {
            getSourceResponse = restHighLevelClient.getSource(getSourceRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return getSourceResponse;
    }

    public GetResponse asyncGetIndexDataById(String indexName, String indexId, String[] includes, String[] excludes, String... fields) {
        GetRequest getRequest = builderGetRequest(indexName, indexId, includes, excludes, fields);

        List<GetResponse> getResponses = Lists.newArrayList();

        restHighLevelClient.getAsync(getRequest, RequestOptions.DEFAULT, new ActionListener<>() {
            @Override
            public void onResponse(GetResponse response) {
                // Called when the execution is successfully completed
                getResponses.add(response);
            }

            @Override
            public void onFailure(Exception e) {
                // Called when the whole GetSourceRequest fails.
                log.error(e.getMessage());
            }
        });
        while (Thread.activeCount() > 1) {
            if (!getResponses.isEmpty()) {
                System.out.println(getResponses.get(0));
                break;
            }
        }
        return getResponses.get(0);
    }

    public GetSourceResponse asyncGetIndexSourceDataById(String indexName, String indexId, String[] includes, String[] excludes) {
        GetSourceRequest getSourceRequest = builderGetSourceRequest(indexName, indexId, includes, excludes);
        List<GetSourceResponse> getSourceResponses = Lists.newArrayList();
        restHighLevelClient.getSourceAsync(getSourceRequest, RequestOptions.DEFAULT, new ActionListener<>() {
            @Override
            public void onResponse(GetSourceResponse response) {
                getSourceResponses.add(response);
            }

            @Override
            public void onFailure(Exception e) {
                log.error(e.getMessage());
            }
        });
        while (Thread.activeCount() > 1) {
            if (!getSourceResponses.isEmpty()) {
                System.out.println(getSourceResponses.get(0));
                break;
            }
        }
        return getSourceResponses.get(0);
    }

    public UpdateResponse updateDataById(String indexName, String indexId, Script inline) {
        UpdateRequest request = new UpdateRequest(indexName, indexId);
        request.script(inline);
        return getUpdateResponse(request);
    }

    public UpdateResponse updateDataById(String indexName, String indexId, String jsonSource) {
        UpdateRequest request = new UpdateRequest(indexName, indexId);
        request.doc(jsonSource, XContentType.JSON);
        return getUpdateResponse(request);
    }

    public UpdateResponse updateDataById(String indexName, String indexId, XContentBuilder builder) {
        UpdateRequest request = new UpdateRequest(indexName, indexId);
        request.doc(builder);
        return getUpdateResponse(request);
    }

    public UpdateResponse updateDataById(String indexName, String indexId, JSONObject obj) {
        UpdateRequest request = new UpdateRequest(indexName, indexId);
        request.doc(obj.toMap());
        return getUpdateResponse(request);
    }

    public DeleteResponse deleteDataById(String indexName, String indexId, Integer version) {
        DeleteRequest request = new DeleteRequest(indexName, indexId);

        // Optional arguments
        request.routing("routing");

        // Timeout to wait for primary shard to become available as a TimeValue
        request.timeout(TimeValue.timeValueMinutes(2));
        // Timeout to wait for primary shard to become available as a String
//        request.timeout("2m");

        // Refresh policy as a WriteRequest.RefreshPolicy instance
        request.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);

        // Refresh policy as a String
//        request.setRefreshPolicy("wait_for");

        if (version != null) {
            request.version(version);
        }

        request.versionType(VersionType.EXTERNAL);

        DeleteResponse response = null;
        try {
            response = restHighLevelClient.delete(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return response;
    }

}
