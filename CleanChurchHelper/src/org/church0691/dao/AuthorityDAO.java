package org.church0691.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.church0691.vo.Authority;
import org.church0691.vo.Member;

public class AuthorityDAO implements IDAO<Authority, String> {

	@Override
	public boolean isExist(String key, Connection conn) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean insert(Authority vo, Connection conn) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(String key, Connection conn) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Authority vo, Connection conn) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Authority selectKey(String key, Connection conn) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Authority> selectAll(Connection conn) throws Exception {
		String sql = "SELECT AUTH_NAME, AUTH_ID, AUTH_DISCRIPTION FROM AUTHORITY";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		List<Authority> list = new ArrayList<>();
		while(rs.next()) {
			Authority vo = new Authority();
			vo.setAuthName(rs.getString(1)); 
			vo.setAuthId(rs.getLong(2));
			vo.setAuthDiscription(rs.getString(3));
			list.add(vo);
		};
		if(conn != null) conn.close();
		return list;
	}
	
//	public int countAll(Connection conn) throws Exception{
//		List<Authority> list = selectAll(conn);
//		return list.size();
//	}

	@Override
	public List<Authority> selectByCondition(Map<String, String> conditionMap, Connection conn) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
