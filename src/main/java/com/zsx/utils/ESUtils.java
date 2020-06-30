package com.zsx.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

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
        System.out.println(hosts);
        System.out.println(scheme);
        try {
            if (restHighLevelClient != null) {
                restHighLevelClient.close();
            }
            //节点
            HttpHost node = new HttpHost("zsx-2.local", 9200, "http");
            RestClientBuilder builder = RestClient.builder(node);
            restHighLevelClient = new RestHighLevelClient(builder);
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }
}
