package seng3011.msm;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CSVParser {
	public static ArrayList<TradeRec> tradeRecs = new ArrayList<TradeRec>();
	public CSVParser(){
		
	}
	
	public static ArrayList<TradeRec> CSVParse(String csvPath){
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		try {
			br = new BufferedReader(new FileReader(csvPath));
			while ((line = br.readLine()) != null) {	 
			    // use comma as separator
				String[] trade = line.split(cvsSplitBy);
				if(!trade[0].equals("#RIC")){
					TradeRec newTradeRec = new TradeRec();				
					DateFormat format = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
					Date date = null;
					try {
						date = format.parse(trade[1]);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					//System.out.println(date);
					//break;
					
					System.out.println(date);
					newTradeRec.setRIC(trade[0]);
					newTradeRec.setDate(date);
					
					newTradeRec.setTime(trade[2]);
					newTradeRec.setType(trade[3]);
					newTradeRec.setQualifier(trade[4]);
					if(trade[5] != null){
						newTradeRec.setOpen(Double.parseDouble(trade[5]));		}			
					if(trade[6] != null)
						newTradeRec.setHigh(Double.parseDouble(trade[6]));
					if(trade[7] != null)
						newTradeRec.setLow(Double.parseDouble(trade[7]));
					if(trade[8] != null)
						newTradeRec.setLast(Double.parseDouble(trade[8]));
					if(trade[9] != null)
						newTradeRec.setVolume(Long.parseLong(trade[9]));
					if(trade[10] != null)
						newTradeRec.setOpenInterest(Double.parseDouble(trade[10]));
					newTradeRec.setSettle(trade[11]);
					newTradeRec.setDataSource(trade[12]);
					tradeRecs.add(newTradeRec);
									
				}
			}
	 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return tradeRecs;		
	}
}
