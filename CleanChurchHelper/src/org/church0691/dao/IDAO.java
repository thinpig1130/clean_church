package org.church0691.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public interface IDAO<T, K>{
	public boolean isExist(K key, Connection conn ) throws Exception;
	public boolean insert(T vo, Connection conn) throws Exception ;
	public boolean delete(K key, Connection conn) throws Exception;
	public boolean update(T vo, Connection conn) throws Exception;
	public T selectKey(K key, Connection conn) throws Exception;
	public List<T> selectAll(Connection conn) throws Exception;
	public List<T> selectByCondition(Map<String, String> conditionMap, Connection conn) throws Exception;
}
