package seng3011.msm;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import seng3011.msm.SellOrder;
import seng3011.msm.TradeRec;

//UNSW CSE SENG3011 Team Awesome Copyright Reserved
public class GenerateOrder {
	private int window;
	private double threshold;
	private String fileName;
	public static Date sDate, eDate;
	static boolean newFile = true;

	public GenerateOrder(int window, double threshold) {
		this.window = window;
		this.threshold = threshold;
	}

	public void generate(ArrayList<TradeRec> tradeRecs) {
		int k = 0;
		SimpleDateFormat timeStamp = new SimpleDateFormat(
				"yyyy-MM-dd HH'H'mm'M'ss'S'");
		Date now = new Date();
		fileName = "SUMMARY " + timeStamp.format(now) + ".csv";
		if (MSrun.outputPath == null || MSrun.outputPath.equals("")) {
			// System.out.println(fileName);
		} else {
			fileName = MSrun.outputPath;
		}
		//Delete file if file already exists.
		File fileTemp = new File(fileName);
		if (fileTemp.exists()) {
			fileTemp.delete();
		}
		HashMap<String, ArrayList<Double>> Rts = new HashMap<String, ArrayList<Double>>();
		HashMap<String, Character> check = new HashMap<String, Character>();
		for (int i = 0; i < tradeRecs.size(); i++) {
			
			if (i == 1) {
				sDate = tradeRecs.get(i).date;
			}
			if (i == tradeRecs.size() - 1) {
				eDate = tradeRecs.get(i).date;
			}
			if (tradeRecs.get(i).last > 0) {
				String ric = tradeRecs.get(i).ric;
				if(!Rts.containsKey(ric)){
					Rts.put(ric, new ArrayList<Double>(window + 2));
					check.put(ric, 'a');
				}
				Rts.get(ric).add(tradeRecs.get(i).last);
				// Calculates SMAt
				//
				
				if (Rts.get(ric).size() == window + 2 ) {
					double TSt = (Rts.get(ric).get(window + 1)/Rts.get(ric).get(window) - Rts.get(ric).get(1)/Rts.get(ric).get(0))/window;
					if(ric.equals("ANZ.AX") && k != 5){
						System.out.println(tradeRecs.get(i).date);
						System.out.println(TSt);
						System.out.println(threshold);
						System.out.println(TSt > threshold);
						k++;
					}
					Rts.get(ric).remove(0);
					if (TSt > threshold) {
						
						SellOrder order = new SellOrder();
						order.setRic(tradeRecs.get(i).getRic());
						order.setDate(tradeRecs.get(i).date);
						order.setTime(tradeRecs.get(i).time);
						order.setSignal('B');
						order.setRic(ric);
						order.setPrice(tradeRecs.get(i).last);
						order.setVolume(100);
						order.setValue(tradeRecs.get(i).last * 100);
						if (order.getSignal() != check.get(ric)) {
							printOrder(order);
							if(ric.equals("ANZ.AX") && k != 5){
								System.out.println("asd");
							}
						}
						check.put(ric, order.getSignal());
					} else if (TSt < -threshold) {
						SellOrder order = new SellOrder();
						order.setRic(tradeRecs.get(i).getRic());
						order.setDate(tradeRecs.get(i).date);
						order.setTime(tradeRecs.get(i).time);
						order.setSignal('S');
						order.setRic(ric);
						order.setPrice(tradeRecs.get(i).last);
						order.setVolume(100);
						order.setValue(tradeRecs.get(i).last * 100);
						if (order.getSignal() != check.get(ric)) {
							printOrder(order);
						}
						check.put(ric, order.getSignal());
					}
				}
				
			}
			
		}

		printLog(1);
	}

	public void printOrder(SellOrder order) {
		try (PrintWriter out = new PrintWriter(new BufferedWriter(
				new FileWriter(fileName, true)))) {
			if (newFile) {
				out.println("#RIC,Date[L],Price,Volume,Value,Signal");
				newFile = false;
			}
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
			String newDateString = sdf.format(order.date);
			out.println(order.ric + "," + newDateString + "," + order.price
					+ "," + order.volume + ","
					+ Math.round(order.value * 100.0) / 100.0 + ","
					+ order.signal);
			// System.out.println(order.ric+","+newDateString+","+order.price+","+order.volume+","+Math.round(order.value*100.0)/100.0+","+order.signal);
		} catch (IOException e) {
		}

	}

	public void printLog(int eCheck) {
		try (PrintWriter out = new PrintWriter(new BufferedWriter(
				new FileWriter("LOG.txt", true)))) {
			out.println("=======================");
			out.println("Developer: Team Awesome");
			out.println("Momentum Strategy Module Version "+MSrun.version);
			out.printf("Input File: %s \n", MSrun.csvPath);
			if (eCheck == 1) {
				out.printf("Parameters: %s %d %f", MSrun.csvPath, window,
						threshold);
				if(!(MSrun.outputPath == null || MSrun.outputPath.equals(""))){
					out.printf(" %s\n",MSrun.outputPath);
				}else{
					out.println();
				}
				out.println("Execution = Successful");
				out.println("Start Date/Time: " + sDate);
				out.println("End Date/Time: " + eDate);
				long difference = eDate.getTime() - sDate.getTime();
				long days = TimeUnit.MILLISECONDS.toDays(difference);
				long year = days/365;
				long month = (days%365)/30;
				long ddays = (days-year*365-month*30);
				out.println("Elapsed Time: "+year + "Year(s)" + month + "Month(s)" + ddays +"Day(s)");
				out.println("Output File: " + fileName);
			}
		} catch (IOException e) {
		}
	}
}