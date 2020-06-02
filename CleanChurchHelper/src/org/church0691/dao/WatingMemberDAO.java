package org.church0691.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.church0691.vo.WatingMember;

public class WatingMemberDAO implements IDAO<WatingMember, String>{

	@Override
	public boolean isExist(String key, Connection conn) throws Exception {
		String sql = "SELECT COUNT(*) FROM WATING_MEMBER WHERE ID=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1,key);
		ResultSet rs = pstmt.executeQuery();
		int cnt=0;
		while(rs.next()) {
			 cnt = rs.getInt(1);
		};
		if(conn != null) conn.close();
		return cnt>0;
	}

	@Override
	public boolean insert(WatingMember vo, Connection conn) throws Exception {
		String sql = "INSERT INTO WATING_MEMBER(ID, NAME, PASSWORD, PHONE, ADDRESS) VALUES(?,?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, vo.getId());
		pstmt.setString(2, vo.getName());
		pstmt.setString(3, vo.getPassword());
		pstmt.setString(4, vo.getPhone());
		if(vo.getAddress().equals("")) {
			pstmt.setString(5, vo.getAddress());
		}else {
			pstmt.setNull(5, Types.VARCHAR);
		}		
		int res = pstmt.executeUpdate();
		if(conn != null) conn.close();
		return res>0 ;
	}

	@Override
	public boolean delete(String key, Connection conn) throws Exception {
		String sql = "DELETE FROM WATING_MEMBER WHERE ID=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, key);
		int res = pstmt.executeUpdate();
		return res>0;
	}

	@Override
	public boolean update(WatingMember vo, Connection conn) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public WatingMember selectKey(String key, Connection conn) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WatingMember> selectAll(Connection conn) throws Exception {
		String sql = "SELECT ID, NAME, PASSWORD, PHONE, ADDRESS FROM WATING_MEMBER";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		List<WatingMember> list = new ArrayList<>();
		while(rs.next()) {
			WatingMember vo = new WatingMember();
			vo.setId(rs.getString(1)); 
			vo.setName(rs.getString(2));
			vo.setPassword(rs.getString(3));
			vo.setPhone(rs.getString(4));
			vo.setAddress(rs.getString(5));
			list.add(vo);
		};
		if(conn != null) conn.close();
		return list;
	}

	@Override
	public List<WatingMember> selectByCondition(Map<String, String> conditionMap, Connection conn) throws Exception {
		String sql="SELECT ID, NAME, PASSWORD, PHONE, ADDRESS "
				+ "FROM WATING_MEMBER WHERE ";
		String sql2="";
		List<WatingMember> list = new ArrayList<>();
		
		for (String x : conditionMap.keySet()) {
			sql2 += x + "=" + conditionMap.get(x) + " AND ";
		}
		sql2 = sql2.substring(0, sql2.length()-5);
		sql += sql2;
		//System.out.println(sql);
		Statement stmt = conn.createStatement();
		ResultSet rs= stmt.executeQuery(sql);
		
		while(rs.next()){
			WatingMember vo = new WatingMember();
			vo.setId(rs.getString(1)); 
			vo.setName(rs.getString(2)); 
			vo.setPassword(rs.getString(3));
			vo.setPhone(rs.getString(4));
			vo.setAddress(rs.getString(5));

			list.add(vo);
		}
		if(conn != null) conn.close();
		return list;
	}

	public int countAll(Connection conn) throws Exception{
		List<WatingMember> list = selectAll(conn);
		return list.size();
	}
}
