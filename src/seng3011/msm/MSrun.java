package seng3011.msm;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
//Momentum Strategy Module Execution module version 1.3
//UNSW CSE SENG3011 Team Awesome Copyright Reserved
public class MSrun {
	//Uasge: java -jar MSM.jar FILE_NAME PARAMETER_FILE_NAME
	public static String csvPath;
	public static String outputPath;
	private static String parametersPath;
	public static String version = "1.3";
	public static void main(String[] args){
		if(args.length == 0){
			System.out.println("Usage: java -jar MSM.jar FILE_NAME PARAMETER_FILE_NAME");
			System.exit(1);
		}
		csvPath = args[0];
		int window = 0;
		double threshold = 0;
		parametersPath = args[1];
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(parametersPath));
			String line = "";
			while ((line = br.readLine()) != null) {	 
				line = line.replaceAll("\\s","");
				String[] tmp = line.split("=");
				if(tmp[0].equals("window")){
					window = Integer.parseInt(tmp[1]);
				}else if(tmp[0].equals("threshold")){
					threshold = Double.parseDouble(tmp[1]);
				}else if(tmp[0].equals("output")){
					outputPath = tmp[1];
				}
			}
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		if(window == 0 || threshold == 0){
			System.out.println("Parameters not enough.");
			System.exit(1);
		}
		ArrayList<TradeRec> tradeRecs = CSVParser.CSVParse(csvPath);
		GenerateOrder strategy = new GenerateOrder(window, threshold);
		ArrayList<SellOrder> sellOrders = strategy.generate(tradeRecs);
		System.out.println("Proceess finished. Please check output files.");
	}
}
