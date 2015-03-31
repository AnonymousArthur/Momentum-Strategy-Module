package seng3011.msm;

import java.util.Date;

public class SellOrder {
	String ric;
	Date date;
	String time;
	double price;
	double volume;
	double value;
	char signal;
	
	public void setRic(String ric){
		this.ric = ric;
	}
	public void setDate(Date date){
		this.date = date;
	}
	public void setTime(String time){
		this.time = time;
	}
	public void setPrice(double price){
		this.price = price;
	}
	public void setVolume(double volume){
		this.volume = volume;
	}
	public void setValue(double value){
		this.value = value;
	}
	public void setSignal(char signal){
		this.signal = signal;
	}
	public char getSignal(){
		return signal;
	}
}
