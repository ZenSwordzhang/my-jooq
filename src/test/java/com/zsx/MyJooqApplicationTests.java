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

}
