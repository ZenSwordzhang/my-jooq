package com.zsx;

import co.elastic.apm.attach.ElasticApmAttacher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
@EnableJpaAuditing
public class MyJooqApplication {

	public static void main(String[] args) {
		ElasticApmAttacher.attach();
		SpringApplication.run(MyJooqApplication.class, args);
	}

}
