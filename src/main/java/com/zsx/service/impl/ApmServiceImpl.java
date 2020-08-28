package com.zsx.service.impl;

import co.elastic.apm.api.ElasticApm;
import co.elastic.apm.api.Span;
import com.zsx.service.ApmService;
import org.springframework.stereotype.Service;

@Service
public class ApmServiceImpl implements ApmService {

    @Override
    public String getNewName(String name) {
        Span span = ElasticApm.currentTransaction().startSpan();
        try {
            span.setName(String.format("ApmServiceImpl.getNewName: %s" , name));
            getOldName(name);
        } catch (Exception e) {
            span.captureException(e);
        } finally {
            span.end();
        }
        return name.toUpperCase();
    }

    @Override
    public String getOldName(String name) {
        Span span = ElasticApm.currentTransaction().startSpan();
        try {
            span.setName(String.format("ApmServiceImpl.getOldName: %s" , name));
        } catch (Exception e) {
            span.captureException(e);
        } finally {
            span.end();
        }
        return name.toLowerCase();
    }
}
