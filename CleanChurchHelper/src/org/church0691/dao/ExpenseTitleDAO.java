package org.church0691.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.church0691.vo.ExpenseTitle;


public class ExpenseTitleDAO implements IDAO<ExpenseTitle, Integer> {

		@Override
	public boolean isExist(Integer key, Connection conn) throws Exception {
		ExpenseTitle vo = selectKey(key, conn);
		if(vo.getId()==0) return false;
		return true;
	}

	@Override
	public boolean insert(ExpenseTitle vo, Connection conn) throws Exception {
		String sql = "INSERT INTO EXPENSE_TITLE(EXP_ID, EXP_TITLE, EXP_SUB_TITLE, EXP_DEL)"
				+ " VALUES(EXPENSE_ID.NEXTVAL,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, vo.getTitle());
		pstmt.setString(2, vo.getSubTitle());
		
		if(vo.getDel()==null || vo.getDel().equals("")) {
			pstmt.setNull(3, Types.VARCHAR);
		}else {
			pstmt.setString(3, vo.getDel());
		}
		
		int res = pstmt.executeUpdate();
		if(conn != null) conn.close();
		return res>0 ;
	}

	@Override
	public boolean delete(Integer key, Connection conn) throws Exception {
		String sql = "DELETE FROM EXPENSE_TITLE WHERE EXP_ID=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setLong(1, key);
		int res = pstmt.executeUpdate();
		return res>0;
	}

	@Override
	public boolean update(ExpenseTitle vo, Connection conn) throws Exception {
		String sql = "UPDATE EXPENSE_TITLE SET EXP_TITLE=?, EXP_SUB_TITLE=?, EXP_DEL=? "
				+ "WHERE EXP_ID=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, vo.getTitle());
		pstmt.setString(2, vo.getSubTitle());
		if(vo.getDel()==null || vo.getDel().equals("")) {
			pstmt.setNull(3, Types.VARCHAR);
		}else {
			pstmt.setString(3, vo.getDel());
		}
		pstmt.setInt(4, vo.getId());
		
		int res = pstmt.executeUpdate();
		if(conn != null) conn.close();
		
		return res>0;
	}

	@Override
	public ExpenseTitle selectKey(Integer key, Connection conn) throws Exception {
		String sql = "SELECT EXP_TITLE, EXP_SUB_TITLE FROM EXPENSE_TITLE WHERE EXP_DEL IS NULL AND EXP_ID=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, key);
		ResultSet rs = pstmt.executeQuery();
		ExpenseTitle vo = new ExpenseTitle();
		while(rs.next()) {
			vo.setId(key);
			vo.setTitle(rs.getString(1));
			vo.setTitle(rs.getString(2));
		};
		if(conn != null) conn.close();
		return vo;
	}

	@Override
	public List<ExpenseTitle> selectAll(Connection conn) throws Exception {
		String sql = "SELECT EXP_ID, EXP_TITLE, EXP_SUB_TITLE FROM EXPENSE_TITLE WHERE EXP_DEL IS NULL";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		List<ExpenseTitle> list = new ArrayList<>();
		while(rs.next()) {
			ExpenseTitle vo = new ExpenseTitle();
			vo.setId(rs.getInt(1));
			vo.setTitle(rs.getString(2));
			vo.setSubTitle(rs.getString(3));
			list.add(vo);
		};
		if(conn != null) conn.close();
		return list;
	}

	@Override
	public List<ExpenseTitle> selectByCondition(Map<String, String> conditionMap, Connection conn) throws Exception {
			String sql="SELECT EXP_ID, EXP_TITLE, EXP_SUB_TITLE FROM EXPENSE_TITLE WHERE EXP_DEL IS NULL AND ";
			String sql2="";
			List<ExpenseTitle> list = new ArrayList<>();
			
			for (String x : conditionMap.keySet()) {
				sql2 += x + "=" + conditionMap.get(x) + " AND ";
			}
			sql2 = sql2.substring(0, sql2.length()-5);
			sql += sql2;

			Statement stmt = conn.createStatement();
			ResultSet rs= stmt.executeQuery(sql);
			
			while(rs.next()){
				ExpenseTitle vo = new ExpenseTitle();
				vo.setId(rs.getInt(1));
				vo.setTitle(rs.getString(2)); 
				vo.setSubTitle(rs.getString(3)); 
				list.add(vo);
			}
			if(conn != null) conn.close();
			return list;
		}

}
