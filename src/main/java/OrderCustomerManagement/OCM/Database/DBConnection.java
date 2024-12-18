package OrderCustomerManagement.OCM.Database;

import java.sql.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class DBConnection {
	 Connection con;
	 Statement st;
	 public DBConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/customerorderdb","root","");
			st = con.createStatement();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		try {
			con.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
