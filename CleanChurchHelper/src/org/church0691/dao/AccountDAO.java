package org.church0691.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.church0691.vo.Account;

public class AccountDAO implements IDAO<Account, String>  {

	@Override
	public boolean isExist(String key, Connection conn) throws Exception {
		Account vo = selectKey(key, conn);
		if(vo.getNo()==null) return false;
		return true;
	}

	@Override
	public boolean insert(Account vo, Connection conn) throws Exception {
		String sql = "INSERT INTO ACCOUNT(ACC_NO, ACC_BANK, ACC_NAME, ACC_PRICE, ACC_INFO)"
				+ " VALUES(?,?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, vo.getNo());
		pstmt.setString(2, vo.getBank());
		pstmt.setString(3, vo.getName());
		pstmt.setLong(4, vo.getPrice());
		pstmt.setString(5, vo.getInfo());
		
		int res = pstmt.executeUpdate();
		if(conn != null) conn.close();
		return res>0 ;
	}

	@Override
	public boolean delete(String key, Connection conn) throws Exception {
		String sql = "DELETE FROM ACCOUNT WHERE ACC_NO=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, key);
		int res = pstmt.executeUpdate();
		return res>0;
	}

	@Override
	public boolean update(Account vo, Connection conn) throws Exception {
		String sql = "UPDATE ACCOUNT SET ACC_BANK=?, ACC_NAME=?, ACC_PRICE=?, ACC_INFO=? "
				+ "WHERE ACC_NO=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, vo.getBank());
		pstmt.setString(2, vo.getName());
		pstmt.setLong(3, vo.getPrice());		
		
		if(vo.getInfo()==null || vo.getInfo().equals("")) {
			pstmt.setNull(4, Types.VARCHAR);
		}else {
			pstmt.setString(4, vo.getInfo());
		}
		
		pstmt.setString(5, vo.getNo());
		
		int res = pstmt.executeUpdate();
		if(conn != null) conn.close();
		
		return res>0;
	}

	@Override
	public Account selectKey(String key, Connection conn) throws Exception {
		String sql = "SELECT ACC_BANK, ACC_NAME, ACC_PRICE, ACC_INFO FROM ACCOUNT WHERE ACC_NO=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, key);
		ResultSet rs = pstmt.executeQuery();
		Account vo = new Account();
		while(rs.next()) {
			vo.setNo(key); 
			vo.setBank(rs.getString(1));
			vo.setName(rs.getString(2));
			vo.setPrice(rs.getLong(3));
			vo.setInfo(rs.getString(4));
		};
		if(conn != null) conn.close();
		return vo;
	}

	@Override
	public List<Account> selectAll(Connection conn) throws Exception {
		String sql = "SELECT ACC_NO, ACC_BANK, ACC_NAME, ACC_PRICE, ACC_INFO FORM ACCOUNT ";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		List<Account> list = new ArrayList<>();
		while(rs.next()) {
			Account vo = new Account();
			vo.setNo(rs.getString(1));
			vo.setBank(rs.getString(2));
			vo.setName(rs.getString(3));
			vo.setPrice(rs.getLong(4));
			vo.setInfo(rs.getString(5));
			list.add(vo);
		};
		if(conn != null) conn.close();
		return list;
	}

	@Override
	public List<Account> selectByCondition(Map<String, String> conditionMap, Connection conn) throws Exception {
		String sql="SELECT ACC_NO, ACC_BANK, ACC_NAME, ACC_PRICE, ACC_INFO FROM ACCOUNT WHERE ";
		String sql2="";
		List<Account> list = new ArrayList<>();
		
		for (String x : conditionMap.keySet()) {
			sql2 += x + "=" + conditionMap.get(x) + " AND ";
		}
		sql2 = sql2.substring(0, sql2.length()-5);
		sql += sql2;

		Statement stmt = conn.createStatement();
		ResultSet rs= stmt.executeQuery(sql);
		
		while(rs.next()){
			Account vo = new Account();
			vo.setNo(rs.getString(1));
			vo.setBank(rs.getString(2));
			vo.setName(rs.getString(3));
			vo.setPrice(rs.getLong(4));
			vo.setInfo(rs.getString(5));
			list.add(vo);
		}
		if(conn != null) conn.close();
		return list;
	}

}
