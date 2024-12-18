package OrderCustomerManagement.OCM.Model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class FileOutErrWriter {
	
	public static void writeErrorFile(List<Order> orderList) {
		String directoryPath = "C:/Users/hp/eclipse-workspace/OrderCustomerManagement/src/main/java/OrderCustomerManagement/data/error/";
		String fileName, fullPath;
		String timestamp;
		//Adding the timestamp for the file's name
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
		timestamp = dateFormat.format(new Date());
		fileName = "error_"+timestamp+".txt";
		//Writing the file in the error dir 
		fullPath = directoryPath + fileName ;
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(fullPath))){
			for(Order order : orderList) {
				bw.write(order.toString());
				bw.newLine();
				bw.write("This order was executed by customers that does not exist in DB");
			}
			bw.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	public static void writeOutputFile(List<Order> orderList){
		String timestamp;
		String directoryPath = "C:/Users/hp/eclipse-workspace/OrderCustomerManagement/src/main/java/OrderCustomerManagement/data/output/";
		String fullPath;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
			timestamp = dateFormat.format(new Date());
			String fileName = "output_"+timestamp+".txt";
			fullPath = directoryPath + fileName;
			FileWriter fw = new FileWriter(new File(fullPath));
			BufferedWriter bw = new BufferedWriter(fw);
			
			//Writing the order in the outputDir
			for (Order order : orderList) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		        String formattedDate = order.getDate().format(formatter);
				bw.write((int)order.getId());
				bw.newLine();
				bw.write(formattedDate);
				bw.newLine();
				bw.write(Double.toString(order.getAmount()));
				bw.newLine();
				bw.write(order.getStatus());
				bw.newLine();
				bw.write(order.getCustomer().toString());
				bw.newLine();
				bw.newLine();
			}
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
