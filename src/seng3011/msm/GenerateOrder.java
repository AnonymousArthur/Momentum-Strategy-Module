package seng3011.msm;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

import seng3011.msm.SellOrder;
import seng3011.msm.TradeRec;


public class  GenerateOrder {
	public static char check = 'a';
	public GenerateOrder(){
		
	}
	public ArrayList<SellOrder> generate(ArrayList<TradeRec> tradeRecs) {
		LinkedList<Double> Rts = new LinkedList<Double>();
		ArrayList<SellOrder> sellOrders = new ArrayList<SellOrder>();
		int n = 3;
		double profit=0;
		System.out.println(tradeRecs.size());
		for(int i=1; i < tradeRecs.size(); i++){
			SellOrder order = new SellOrder();
			order.setRic(tradeRecs.get(i).getRic());
			order.setDate(tradeRecs.get(i).date);
			order.setTime(tradeRecs.get(i).time);
			
			if(tradeRecs.get(i).last > 0){
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
				if(Rts.size() == n + 1){
					double SMAtCurr=0;
					double SMAtPrev=0;
					for(j=0; j != n; j++){
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
		return sellOrders;
	}
	static private double threshold = 0.001;
	
	public void printCSV(){
		try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("SUMMARY.csv", true)))) {
		    out.println("#RIC,Date/Time,Price,Volume,Value,Bid/Ask");
		}catch (IOException e) {
		}
	}
	public void printOrder(SellOrder order){

		try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("SUMMARY.csv", true)))) {
		    out.println(order.ric+","+order.date+order.time+","+order.price+","+order.volume+","+Math.round(order.value*100.0)/100.0+","+order.signal);
		}catch (IOException e) {
		}
		
		//System.out.println(order.ric+","+order.date+order.time+","+order.price+","+order.volume+","+Math.round(order.value*100.0)/100.0+","+order.signal);

				
	}

}
