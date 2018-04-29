package com.yusys;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.OracleTypes;
import oracle.jdbc.driver.OracleCallableStatement;

import org.junit.Test;

public class TestProcedure {
	/* --������Ĵ洢���̣���ѯѧ�����������Ա����䣬רҵ
 create or replace procedure getstuinfo(eno in number,
 pname out varchar2,psex out varchar2,page out number,pmajor out varchar2)*/
	//Ӧ�ó�����ô洢����
	@Test
	public void Testpro(){
		String sql="{call getstuinfo(?,?,?,?,?)}";
	Connection conn=null;
	CallableStatement call=null;
	
	try {
		conn=JdbcUtil.getConnection();
		call=conn.prepareCall(sql);
		call.setInt(1, 12);
		//����out��������
		call.registerOutParameter(2, OracleTypes.VARCHAR);
		call.registerOutParameter(3, OracleTypes.VARCHAR);
		call.registerOutParameter(4, OracleTypes.NUMBER);
		call.registerOutParameter(5, OracleTypes.VARCHAR);
		//����ִ��
		call.execute();
		//ȡ�����
		String name=call.getString(2);
		String sex=call.getString(3);
		int age=call.getInt(4);
		String major=call.getString(5);
		System.out.println(name+"�����䡢�Ա�רҵ�ֱ���"+age+"=="
				+ "=="+sex+"==="+major);
		
		
	} catch (Exception e) {
		// TODO: handle exception
	}
	}
//Ӧ�ó�����ô洢����
	@Test
	public void TestFunction(){
//		create or replace function querymajor(eno in number)
	//	return varchar2
		String sql="{?=call querymajor(?)}";
		Connection conn=null;
		CallableStatement call=null;
		
			
			try {
				conn=JdbcUtil.getConnection();
				call=conn.prepareCall(sql);
				
				call.registerOutParameter(1, OracleTypes.VARCHAR);
				call.setInt(2, 12);
				call.execute();
				String major=call.getString(1);
				System.out.println("רҵ��hi"+major);
				} catch (Exception e) {
					// TODO: handle exception
				}
			
		
	}
//	��Ӧ�ó����з��ʰ��µĴ洢����
	@Test
	public void testcusor(){
		String sql = "{call mypackage.queryStuinfo(?,?)}";
		Connection conn=null;
		CallableStatement call=null;
		ResultSet rs=null;
		try {
			conn=JdbcUtil.getConnection();
			call=conn.prepareCall(sql);
			call.setInt(1, 9);
			call.registerOutParameter(2, OracleTypes.CURSOR);
			call.execute();
			rs=((OracleCallableStatement)call).getCursor(2);
			while(rs.next()){
				int  id=rs.getInt("id");
				String name=rs.getString("name");
				String major=rs.getString("major");
				int age= rs.getInt("age");
				System.out.println(name+"��רҵ"+major+"����"+age);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtil.release(conn, call, rs);
		}
		
	}
}
