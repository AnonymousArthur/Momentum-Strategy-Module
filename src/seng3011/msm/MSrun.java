package seng3011.msm;

import java.util.ArrayList;

public class MSrun {
	//Uasge: java Momentum-Strategy-Module FILE_NAME
	public static void main(String[] args){
		String csvPath = args[0];
		ArrayList<TradeRec> tradeRecs = CSVParser.CSVParse(csvPath);
	}
	
}
