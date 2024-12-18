package OrderCustomerManagement.OCM.Model;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ParseJsonThread extends Thread  {
	public List<JSONObject> parseJsonFile(File fileName) throws ParseException  {
	    	List<JSONObject> JSONObjectList = new ArrayList <JSONObject>();
	    	JSONParser parser = new JSONParser();
	    	//Parsing jsonOrder Array to a list of objects
	    	  try (FileReader fileReader = new FileReader(fileName)) {
	    	        BufferedReader bufferedReader = new BufferedReader(fileReader);
	                JSONArray jsonArray = (JSONArray) parser.parse(fileReader);
	    	        for (Object obj : jsonArray) {
	                    JSONObject jsonObject = (JSONObject) obj;
	                    JSONObjectList.add(jsonObject);
	                }
	    	        bufferedReader.close();       
	    	        return JSONObjectList;
	    	    }
	    	    catch(FileNotFoundException | ParseException ex) {
	    	        System.out.println("Unable to open file '" + fileName + "'");                
	    	    }
	    	    catch(IOException ex) {
	    	        System.out.println("Error reading file '" + fileName + "'");                  
	    	    }
	    	  return JSONObjectList;
	    }
	
	public void ManageCustomerOrder(File file){	
		List<JSONObject> listOrder = null;
		List <Order> outputOrderList = new ArrayList<Order>(); //Contains all orders done by our customers
		List <Order> errorOrderList = new ArrayList<Order>(); //Contains all orders done by other customers
		OrderDaoImp odi = new OrderDaoImp();
		CustomerDaoImp cdi = new CustomerDaoImp();

		try {	
			listOrder = parseJsonFile(file);
			// Transforming jsonObject into an OrderObject
			for (JSONObject obj : listOrder) {
				String jsonDate = (String)(obj.get("date"));
				double orderAmount = (double)(obj.get("amount"));
				String orderStatus = (String)(obj.get("status"));
				long customerId = (Long)(obj.get("customer_id"));
				
				// Parsing the jsonDate to LocalDate
				LocalDate orderDate = LocalDate.parse(jsonDate);
				
				//Creating the order Object 
	       	    Customer customer = cdi.getCustomerfromDB(customerId);
	       	    Order newOrder = new Order(orderDate,orderAmount,orderStatus,customer);
	       	    
				//Verify if the order has a customer_id that exist in DB
				if(cdi.doesCustomerExistsInDB(customerId)) {
		        	 outputOrderList.add(newOrder);
		        	 odi.addOrder(newOrder); //Add Order to DB
		        	 file.delete();
		        	 
		        	 //Just to verify everything works from console
			         System.out.println("the file was deleted ");
			         System.out.println("the output List has been created ");	
			         
		        } else {
		        	errorOrderList.add(newOrder);
		        	file.delete();
		        	//console
		        	System.out.println("the customer does not exist in our DB ");
		        	System.out.println("the file was deleted ");
		        }
				FileOutErrWriter.writeErrorFile(errorOrderList);
				FileOutErrWriter.writeOutputFile(outputOrderList);
			}
		} catch (ParseException e ) {
			e.printStackTrace();
		}
	}
	
	public void parseJsonThread() {
		 while(true) {
			  try {
				 //Reading all files in the input directory 
				  File inputDir = new File("C:/Users/hp/eclipse-workspace/OrderCustomerManagement/src/main/java/OrderCustomerManagement/data/input");
				 // Using the listFiles's File method
				  File[] inputFiles = inputDir.listFiles();
				  for (File file : inputFiles) {
					  System.out.println("file name : "+file.getName());
					  System.out.println("----------------------");
					  ManageCustomerOrder(file);
				  }
				  sleep(60000); // To test : One minute
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		 }
	}

	
	public void run() {
		parseJsonThread();
	}
}

