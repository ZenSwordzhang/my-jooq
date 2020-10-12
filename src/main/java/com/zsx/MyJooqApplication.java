package com.zsx;

import co.elastic.apm.attach.ElasticApmAttacher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class MyJooqApplication {

	public static void main(String[] args) {
		ElasticApmAttacher.attach();
		SpringApplication.run(MyJooqApplication.class, args);
	}

}
