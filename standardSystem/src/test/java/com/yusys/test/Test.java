package com.yusys.test;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import junit.framework.TestCase;

public class Test extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}
	public void test(){
		ApplicationContext acc= new ClassPathXmlApplicationContext("classpath:applicationContext-mybatis.xml");
		BasicDataSource bds = (BasicDataSource)acc.getBean("dataSource");
		SqlSessionFactory ssf= (SqlSessionFactory)acc.getBean("sqlSession");
		System.out.println("数据库连接："+bds);
		System.out.println("sqlSession工厂：---->"+ssf);
	}
}
