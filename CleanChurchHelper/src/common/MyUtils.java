package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public interface MyUtils {
	public static Connection getConn(){
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:xe","accountmanager","accountmanager"
					);
			return conn;
		} catch (ClassNotFoundException e) {
			System.err.println("OracleDriver 생성오류");
			System.exit(0);
		} catch (SQLException e) {
			System.err.println("conn 객체를 생성할  수 없습니다.");
		}
		return conn;
	}		

}
