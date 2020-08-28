package com.zsx.controller;

import co.elastic.apm.api.ElasticApm;
import co.elastic.apm.api.Transaction;
import com.zsx.service.ApmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/apm")
@RestController
public class ApmAgentController {

    @Autowired
    private ApmService apmService;

    @GetMapping("/test/{name}")
    public String getName(@PathVariable String name) {
        String newName = null;
        Transaction transaction = ElasticApm.currentTransaction();
        System.out.println(transaction.getTraceId());
        try {
            transaction.setName(name);
            transaction.setType("test");
            newName = apmService.getNewName(name);
        } catch (Exception e) {
            transaction.captureException(e);
        } finally {
            transaction.end();
        }
        return newName;
    }

}
