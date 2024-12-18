package OrderCustomerManagement.OCM.Model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class Order {
	private long id;
	private LocalDate date;
	private double amount;
	private String status;
	Customer customer;
	
	Order(LocalDate orderDate,double orderAmount,String orderStatus,Customer customer){
		this.amount = orderAmount;
		this.date=orderDate;
		this.status = orderStatus;
		this.customer = customer;
	};
	
}
