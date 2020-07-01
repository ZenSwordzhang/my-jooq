package com.zsx;

import org.junit.jupiter.api.Test;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MyJooqApplicationTests {

	@Test
	void testIsEmpty() {
		assertTrue(CollectionUtils.isEmpty(List.of()));
	}

	@Test
	void testSplit() {
		String addr = "192.168.1.110:9200";
		System.out.println(addr.split(":")[0]);
		System.out.println(addr.split(":")[1]);
	}

}
