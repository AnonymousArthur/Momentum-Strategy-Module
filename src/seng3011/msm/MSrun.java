package seng3011.msm;

import java.util.ArrayList;
import java.util.LinkedList;

public class MSrun {
	//Uasge: java Momentum-Strategy-Module FILE_NAME
	public static void main(String[] args){
		String csvPath = args[0];
		int window = Integer.parseInt(args[1]);
		double threshold = Double.parseDouble(args[2]);
		ArrayList<TradeRec> tradeRecs = CSVParser.CSVParse(csvPath);
		GenerateOrder strategy = new GenerateOrder(window, threshold);
		ArrayList<SellOrder> sellOrders = strategy.generate(tradeRecs);
	}
}
