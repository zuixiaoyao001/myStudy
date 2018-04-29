package com.yusys;

import java.sql.Connection;

import org.junit.Before;
import org.junit.Test;

public class UnitTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		Connection conn=JdbcUtil.getConnection();
		System.out.println(conn);
	}

}
