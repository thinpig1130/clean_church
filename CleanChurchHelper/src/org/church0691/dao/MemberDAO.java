package org.church0691.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.church0691.vo.Member;

public class MemberDAO implements IDAO<Member, String> {

	@Override
	public boolean isExist(String key, Connection conn) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean insert(Member vo, Connection conn) throws Exception {
		String sql = "INSERT INTO MEMBER(MEM_NO, MEM_PASSWORD, MEM_ID,"
				+ " MEM_NAME, MEM_PHONE, MEM_ADDRESS, MEM_REMARK, MEM_DEL, AUTH_NAME) VALUES(?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, vo.getMemNo());
		if(vo.getMemPassword()==null || vo.getMemPassword().equals("")) {
			pstmt.setNull(2, Types.VARCHAR);
		}else {
			pstmt.setString(2, vo.getMemPassword());
		}
		if(vo.getMemId()==null  || vo.getMemId().equals("")) {
			pstmt.setNull(3, Types.VARCHAR);
		}else {
			pstmt.setString(3, vo.getMemId());
		}
		pstmt.setString(4, vo.getMemName());
		pstmt.setString(5, vo.getMemPhone());
		if(vo.getMemAddress()==null || vo.getMemAddress().equals("")  ) {
			pstmt.setNull(6, Types.VARCHAR);
		}else {
			pstmt.setString(6, vo.getMemAddress());
		}
		if(vo.getMemRemark()==null || vo.getMemRemark().equals("") ) {
			pstmt.setNull(7, Types.VARCHAR);
		}else {
			pstmt.setString(7, vo.getMemRemark());
		}
		pstmt.setNull(8, Types.VARCHAR);
		pstmt.setString(9, vo.getAuthName());
		
		int res = pstmt.executeUpdate();
		if(conn != null) conn.close();
		
		return res>0 ;
	}

	@Override
	public boolean delete(String key, Connection conn) throws Exception {
		String sql = "UPDATE MEMBER SET MEM_DEL='삭제' "
				+ "WHERE MEM_NO=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, key);
		
		int res = pstmt.executeUpdate();
		if(conn != null) conn.close();
		return res>0 ;
	}

	@Override
	public boolean update(Member vo, Connection conn) throws Exception {
		String sql = "UPDATE MEMBER SET "
				+ "MEM_NAME=?, MEM_PHONE=?, MEM_ADDRESS=?, MEM_REMARK=?, "
				+ "AUTH_NAME=?, MEM_ID=?, MEM_PASSWORD=? WHERE MEM_NO=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		System.out.println(vo);
		pstmt.setString(1, vo.getMemName());
		pstmt.setString(2, vo.getMemPhone());
		if(vo.getMemAddress()==null || vo.getMemAddress().equals("")  ) {
			pstmt.setNull(3, Types.VARCHAR);
		}else {
			pstmt.setString(3, vo.getMemAddress());
		}
		if(vo.getMemRemark()==null || vo.getMemRemark().equals("") ) {
			pstmt.setNull(4, Types.VARCHAR);
		}else {
			pstmt.setString(4, vo.getMemRemark());
		}
		pstmt.setString(5, vo.getAuthName());
		
		if(vo.getMemId()==null || vo.getMemId().equals("") ) {
			pstmt.setNull(6, Types.VARCHAR);
		}else {
			pstmt.setString(6, vo.getMemId());
		}
		
		if(vo.getMemPassword()==null || vo.getMemPassword().equals("") ) {
			pstmt.setNull(7, Types.VARCHAR);
		}else {
			pstmt.setString(7, vo.getMemPassword());
		}
		
		pstmt.setString(8, vo.getMemNo());
		
		int res = pstmt.executeUpdate();
		if(conn != null) conn.close();
		
		return res>0 ;
	}

	@Override
	public Member selectKey(String key, Connection conn) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Member> selectAll(Connection conn) throws Exception {
		String sql = "SELECT MEM_NO, MEM_ID, MEM_NAME, MEM_PHONE, MEM_ADDRESS, MEM_REMARK, AUTH_NAME FROM MEMBER WHERE MEM_DEL IS NULL";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		List<Member> list = new ArrayList<>();
		while(rs.next()) {
			Member vo = new Member();
			vo.setMemNo(rs.getString(1)); //MEM_NO
			vo.setMemId(rs.getString(2));//MEM_ID
			vo.setMemName(rs.getString(3)); //MEM_NAME
			vo.setMemPhone(rs.getString(4)); //MEM_PHONE
			vo.setMemAddress(rs.getString(5)); //MEM_ADDRESS
			vo.setMemRemark(rs.getString(6)); //MEM_REMARK
			vo.setAuthName(rs.getString(7)); // AUTH_NAME
			list.add(vo);
		};
		if(conn != null) conn.close();
		return list;
	}

	public List<Member> selectNull(String attTitle, Connection conn) throws Exception {
		String sql = "SELECT MEM_NO, MEM_ID, MEM_NAME, MEM_PHONE, MEM_ADDRESS, MEM_REMARK, AUTH_NAME FROM MEMBER WHERE MEM_DEL IS NULL AND "+ attTitle + " IS NULL";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		//pstmt.setString(1, attTitle);
		ResultSet rs = pstmt.executeQuery();
		List<Member> list = new ArrayList<>();
		while(rs.next()) {
			Member vo = new Member();
			vo.setMemNo(rs.getString(1)); //MEM_NO
			vo.setMemId(rs.getString(2));//MEM_ID
			vo.setMemName(rs.getString(3)); //MEM_NAME
			vo.setMemPhone(rs.getString(4)); //MEM_PHONE
			vo.setMemAddress(rs.getString(5)); //MEM_ADDRESS
			vo.setMemRemark(rs.getString(6)); //MEM_REMARK
			vo.setAuthName(rs.getString(7)); // AUTH_NAME
			list.add(vo);
		};
		if(conn != null) conn.close();
		return list;
	}
	
	@Override
	public List<Member> selectByCondition(Map<String, String> conditionMap, Connection conn) throws Exception {
		String sql="SELECT MEM_NO, MEM_PASSWORD, MEM_ID,"
				+ " MEM_NAME, MEM_PHONE, MEM_ADDRESS, MEM_REMARK, MEM_DEL, AUTH_NAME FROM MEMBER WHERE ";
		String sql2="";
		List<Member> list = new ArrayList<>();
		
		for (String x : conditionMap.keySet()) {
			sql2 += x + "=" + conditionMap.get(x) + " AND ";
		}
		sql2 = sql2.substring(0, sql2.length()-5);
		sql += sql2;
		//System.out.println(sql);
		Statement stmt = conn.createStatement();
		ResultSet rs= stmt.executeQuery(sql);
		
		while(rs.next()){
			Member vo = new Member();
			vo.setMemNo(rs.getString(1)); //MEM_NO
			vo.setMemPassword(rs.getString(2)); //MEM_PASSWORD
			vo.setMemId(rs.getString(3));//MEM_ID
			vo.setMemName(rs.getString(4)); //MEM_NAME
			vo.setMemPhone(rs.getString(5)); //MEM_PHONE
			vo.setMemAddress(rs.getString(6)); //MEM_ADDRESS
			vo.setMemRemark(rs.getString(7)); //MEM_REMARK
			vo.setMemDel(rs.getString(8)); //MEM_DEL
			vo.setAuthName(rs.getString(9)); // AUTH_NAME
			list.add(vo);
		}
		if(conn != null) conn.close();
		return list;
	}
	
	public int countAll(Connection conn) throws Exception{
		String sql = "SELECT COUNT(*) FROM MEMBER";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		int cnt=0;
		while(rs.next()) {
			 cnt = rs.getInt(1);
		};
		if(conn != null) conn.close();
		return cnt;
	}

}
