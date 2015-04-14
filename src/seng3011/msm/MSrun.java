package seng3011.msm;

import java.util.ArrayList;
import java.util.LinkedList;
//Momentum Strategy Module Execution module version 1.1
//UNSW CSE SENG3011 Team Awesome Copyright Reserved
public class MSrun {
	//Uasge: java -jar MSM.jar FILE_NAME
	public static String csvPath;
	public static void main(String[] args){
		if(args.length == 0){
			System.out.println("Usage: java -jar MSM.jar FILE_NAME");
			System.exit(1);
		}
		csvPath = args[0];
		int window = Integer.parseInt(args[1]);
		double threshold = Double.parseDouble(args[2]);
		ArrayList<TradeRec> tradeRecs = CSVParser.CSVParse(csvPath);
		GenerateOrder strategy = new GenerateOrder(window, threshold);
		ArrayList<SellOrder> sellOrders = strategy.generate(tradeRecs);
		System.out.println("Proceess finished. Please check output files.");
	}
}
