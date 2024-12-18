package OrderCustomerManagement.OCM;

import OrderCustomerManagement.OCM.Model.ParseJsonThread;

public class App {
    public static void main(String[] args) {
    	ParseJsonThread psj = new ParseJsonThread();
    	psj.start();
    	
    	
    }
}
