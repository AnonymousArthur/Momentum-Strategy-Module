package seng3011.msm;
import java.io.*;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Date;

import seng3011.msm.SellOrder;
import seng3011.msm.TradeRec;

//UNSW CSE SENG3011 Team Awesome Copyright Reserved
public class  GenerateOrder {
	private int window;
	private double threshold;
	private String fileName;
	public static char check = 'a';
	public static Date sDate, eDate;
	static boolean newFile = true;
	public GenerateOrder(int window, double threshold){
		this.window = window;
		this.threshold = threshold;
	}
	public ArrayList<SellOrder> generate(ArrayList<TradeRec> tradeRecs) {
		SimpleDateFormat timeStamp = new SimpleDateFormat("yyyy-MM-dd HH#mm#ss");
		Date now = new Date();
		fileName = "SUMMARY "+ timeStamp.format(now)+".csv";
		LinkedList<Double> Rts = new LinkedList<Double>();
		ArrayList<SellOrder> sellOrders = new ArrayList<SellOrder>();
		for(int i=1; i < tradeRecs.size(); i++){
			SellOrder order = new SellOrder();
			order.setRic(tradeRecs.get(i).getRic());
			order.setDate(tradeRecs.get(i).date);
			order.setTime(tradeRecs.get(i).time);
			if(i==1){
				sDate = tradeRecs.get(i).date;
			}
			if(i==tradeRecs.size()-1){
				eDate = tradeRecs.get(i).date;
			}

			if(tradeRecs.get(i).last > 0){
				order.setRic(tradeRecs.get(i).ric);
				order.setPrice(tradeRecs.get(i).last);
				order.setVolume(100);
				order.setValue(tradeRecs.get(i).last*100);
				
				//Calculates Rt at this point, ignores previous day if no trades that day.
				int j=i-1;
				for(; j>=0 && tradeRecs.get(i).last==0;j--);
				if(j>=0){
					double Rt = (tradeRecs.get(i).last - tradeRecs.get(j).last)/tradeRecs.get(i).last;
					Rts.add(Rt);
				}
				//Caclulates SMAt
				//
				if(Rts.size() == window + 1){
					double SMAtCurr=0;
					double SMAtPrev=0;
					for(j=0; j != window; j++){
						SMAtPrev += Rts.get(j);
						SMAtCurr += Rts.get(j+1);
					}
					Rts.remove(0);
					double TSt = (SMAtCurr - SMAtPrev);
					if(TSt>threshold){
						order.setSignal('B');
						if(order.getSignal() != check){
							printOrder(order);
						}
						check = order.getSignal();
					} else if (TSt<-threshold){
						order.setSignal('S');
						if(order.getSignal() != check){
							printOrder(order);
						}
						check = order.getSignal();
					}
					
				}
			}
			
			sellOrders.add(order);
		}
		printLog(1);
		return sellOrders;
	}


	
	public void printOrder(SellOrder order){
		try(PrintWriter out =  new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)))) {
			if(newFile){
				out.println("#RIC,Date[L],Price,Volume,Value,Signal");
				newFile = false;
			}
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String newDateString = sdf.format(order.date);
		    out.println(order.ric+","+newDateString+","+order.price+","+order.volume+","+Math.round(order.value*100.0)/100.0+","+order.signal);
		    //System.out.println(order.ric+","+newDateString+","+order.price+","+order.volume+","+Math.round(order.value*100.0)/100.0+","+order.signal);		
		}catch (IOException e) {
		}
		
				
	}
	
	public void printLog(int eCheck){
		try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("LOG.txt", true)))) {
			out.println("=======================");
			out.println("Developer: Team Awesome");
			out.println("Momentum Strategy Module Version 1.1");
			out.printf("Input File: %s \n", MSrun.csvPath);
			if(eCheck == 1){			
				out.printf("Parameters: %s %d %f \n", MSrun.csvPath,window, threshold);
				out.println("Execution = Successful");
				out.println("Start Date/Time:"+sDate);
				out.println("End Date/Time:"+eDate);
				long difference = eDate.getTime() - sDate.getTime();
				Date eTime = new Date(difference);
				Format frmt = new SimpleDateFormat("yy MM dd HH:mm:ss");
				out.println("Elapsed Time:"+frmt.format(eTime).toString());
				out.println("Output File: "+fileName);
			}
		}catch (IOException e) {
		}
	
	}

}