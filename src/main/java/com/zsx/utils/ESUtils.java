package com.zsx.utils;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Cancellable;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.GetSourceRequest;
import org.elasticsearch.client.core.GetSourceResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
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

        getSourceRequest.routing("routing");
        getSourceRequest.preference("preference");

        // Set realtime flag to false (true by default)
        getSourceRequest.realtime(false);

        // Perform a refresh before retrieving the document (false by default)
//        getSourceRequest.refresh(true);
        return getSourceRequest;
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

    public GetResponse getResponseById(String indexName, String indexId, String[] includes, String[] excludes, String... fields) {
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

    public GetSourceResponse getSourceResponseById(String indexName, String indexId, String[] includes, String[] excludes) {
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

    public GetResponse asyncGetResponseById(String indexName, String indexId, String[] includes, String[] excludes, String... fields) {
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

    public GetSourceResponse asyncGetSourceResponseById(String indexName, String indexId, String[] includes, String[] excludes) {
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

}
