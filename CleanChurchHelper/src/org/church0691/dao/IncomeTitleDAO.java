package org.church0691.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.church0691.vo.IncomeTitle;

public class IncomeTitleDAO implements IDAO<IncomeTitle, Integer> {

	@Override
	public boolean isExist(Integer key, Connection conn) throws Exception {
		IncomeTitle vo = selectKey(key, conn);
		if(vo.getId()==0) return false;
		return true;
	}

	@Override
	public boolean insert(IncomeTitle vo, Connection conn) throws Exception {
		String sql = "INSERT INTO INCOME_TITLE(INC_ID, INC_TITLE, INC_DEL)"
				+ " VALUES(INCOME_ID.NEXTVAL,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, vo.getTitle());
		
		if(vo.getDel()==null || vo.getDel().equals("")) {
			pstmt.setNull(2, Types.VARCHAR);
		}else {
			pstmt.setString(2, vo.getDel());
		}
		int res = pstmt.executeUpdate();
		if(conn != null) conn.close();
		return res>0 ;
	}

	@Override
	public boolean delete(Integer key, Connection conn) throws Exception {
		String sql = "DELETE FROM INCOME_TITLE WHERE INC_ID=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setLong(1, key);
		int res = pstmt.executeUpdate();
		return res>0;
	}

	@Override
	public boolean update(IncomeTitle vo, Connection conn) throws Exception {
		String sql = "UPDATE INCOME_TITLE SET INC_TITLE=?, INC_DEL=? "
				+ "WHERE INC_ID=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, vo.getTitle());
		if(vo.getDel()==null || vo.getDel().equals("")) {
			pstmt.setNull(2, Types.VARCHAR);
		}else {
			pstmt.setString(2, vo.getDel());
		}
		pstmt.setInt(3, vo.getId());
		
		int res = pstmt.executeUpdate();
		if(conn != null) conn.close();
		
		return res>0;
	}

	@Override
	public IncomeTitle selectKey(Integer key, Connection conn) throws Exception {
		String sql = "SELECT INC_TITLE FROM INCOME_TITlE WHERE INC_DEL IS NULL AND INC_ID=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, key);
		ResultSet rs = pstmt.executeQuery();
		IncomeTitle vo = new IncomeTitle();
		while(rs.next()) {
			vo.setId(key); //INC_ID
			vo.setTitle(rs.getString(1));//INC_TITLE
		};
		if(conn != null) conn.close();
		return vo;
	}

	@Override
	public List<IncomeTitle> selectAll(Connection conn) throws Exception {
		String sql = "SELECT INC_ID, INC_TITLE FROM INCOME_TITlE WHERE INC_DEL IS NULL";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		List<IncomeTitle> list = new ArrayList<>();
		while(rs.next()) {
			IncomeTitle vo = new IncomeTitle();
			vo.setId(rs.getInt(1)); //INC_ID
			vo.setTitle(rs.getString(2));//INC_TITLE
			list.add(vo);
		};
		if(conn != null) conn.close();
		return list;
	}

	@Override
	public List<IncomeTitle> selectByCondition(Map<String, String> conditionMap, Connection conn) throws Exception {
		String sql="SELECT INC_ID, INC_TITLE FROM INCOME_TITlE WHERE INC_DEL IS NULL AND ";
		String sql2="";
		List<IncomeTitle> list = new ArrayList<>();
		
		for (String x : conditionMap.keySet()) {
			sql2 += x + "=" + conditionMap.get(x) + " AND ";
		}
		sql2 = sql2.substring(0, sql2.length()-5);
		sql += sql2;

		Statement stmt = conn.createStatement();
		ResultSet rs= stmt.executeQuery(sql);
		
		while(rs.next()){
			IncomeTitle vo = new IncomeTitle();
			vo.setId(rs.getInt(1));
			vo.setTitle(rs.getString(2)); 
			list.add(vo);
		}
		if(conn != null) conn.close();
		return list;
	}

}
