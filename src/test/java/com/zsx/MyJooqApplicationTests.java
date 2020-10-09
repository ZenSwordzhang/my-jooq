package com.zsx;

import org.junit.jupiter.api.Test;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MyJooqApplicationTests {

	@Test
	void testIsEmpty() {
		assertTrue(CollectionUtils.isEmpty(List.of()));
	}

	@Test
	void testSplit() {
		String addr = "192.168.1.110:9200";
		assertEquals("192.168.1.110", addr.split(":")[0]);
		assertEquals("9200", addr.split(":")[1]);
	}

}
