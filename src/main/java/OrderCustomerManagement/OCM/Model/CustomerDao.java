package OrderCustomerManagement.OCM.Model;

public interface CustomerDao {
	public Customer getCustomerfromDB(long customerId);
	public boolean doesCustomerExistsInDB(long customerId);
}
