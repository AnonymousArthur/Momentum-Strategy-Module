package seng3011.msm;
import java.util.ArrayList;
import java.util.LinkedList;

import seng3011.msm.SellOrder;
import seng3011.msm.TradeRec;


public class  GenerateOrder {
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
						order.setSignal('b');
					} else if (TSt<-threshold){
						order.setSignal('s');
					}
					
				}
			}
			printOrder(order);
			sellOrders.add(order);
		}
		return sellOrders;
	}
	static private double threshold = 0.001;
	
	public void printOrder(SellOrder order){
		switch (order.signal) {
		case 'b':
			System.out.println("BUY "+order.date+" "+order.price);
			break;

		case 's':
			System.out.println("SELL "+order.date+" "+order.price);
			break;
			
		default:
			
			break;
			
		}
		
	}
}
