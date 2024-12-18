package OrderCustomerManagement.OCM.Model;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import OrderCustomerManagement.OCM.Database.DBConnection;

public class OrderDaoImp implements OrderDao {
	@Override
	public void addOrder(Order order) {
		DBConnection db = new DBConnection();
		PreparedStatement ps;
		long orderId = order.getId();
		LocalDate orderDate = order.getDate();
		Double orderAmount = order.getAmount();
		String orderStatus = order.getStatus();
		int customerId = order.getCustomer().getId();
		try {
			String Query = "INSERT INTO orders (id, date, amount, status, customer_id) VALUES (?, ?, ?, ?, ?)";
			ps  = db.getCon().prepareStatement(Query);
			ps.setLong(1, orderId);
			ps.setDate(2, Date.valueOf(orderDate));
			ps.setDouble(3, orderAmount);
			ps.setString(4,orderStatus);
			ps.setInt(5,customerId);
			ps.execute();
			System.out.print("The order has been inserted into Database");
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
