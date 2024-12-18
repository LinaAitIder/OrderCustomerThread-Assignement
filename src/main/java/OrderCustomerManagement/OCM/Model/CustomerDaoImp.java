package OrderCustomerManagement.OCM.Model;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import OrderCustomerManagement.OCM.Database.DBConnection;


public class CustomerDaoImp implements CustomerDao {
	DBConnection db ;
	
	@Override 
	public boolean doesCustomerExistsInDB(long customerId){
		boolean customerExists = false;
		db = new DBConnection();
		ResultSet res ;
		PreparedStatement pst;
		
		try {
			String request = "Select * From customers Where id=? ";
			pst = db.getCon().prepareStatement(request);
			pst.setLong(1,customerId);
			res = pst.executeQuery();
			if((res.next())) {
				System.out.println("the customer exist in database");
				return true;
			} else {
				System.out.println("the customer does not exist in database");
				return false;
			}
		}catch(SQLException e) {
			System.out.println("Database or sql Problem!");
		}
		return customerExists;
	}
	
	@Override
	public Customer getCustomerfromDB(long customerId) {
		Customer customer = null;
		ResultSet res;
		PreparedStatement pst ;
		db = new DBConnection();
		try {
			String Query="SELECT * FROM customers where id=?";
			pst = db.getCon().prepareStatement(Query);
			pst.setLong(1,customerId);
			res = pst.executeQuery();
			if(res.next()) {
				return (new Customer(res.getInt(1),res.getString(2), res.getString(3), res.getString(4)));
			} else {
				System.out.println("This customer does not exit in databse");
				return null;
			}
		}catch(SQLException e) {
			System.out.println("SQL or DB problem");
		}
		return customer;
	}
	
	
}
