package seng3011.msm;

import java.util.Date;

public class TradeRec {
	String ric;
	public Date date;
	public String time;
	String type;
	String qualifier;
	double open;
	double high;
	double low;
	public double last;
	long volume;
	double openInterest;
	String settle;
	String dataSource;
	
	public TradeRec() {
		
	}

	public void setRIC(String string) {
		// TODO Auto-generated method stub
		ric = string;
	}

	public void setDate(Date date2) {
		// TODO Auto-generated method stub
		date = date2;
	}

	public void setTime(String string) {
		// TODO Auto-generated method stub
		time = string;
	}

	public void setType(String string) {
		// TODO Auto-generated method stub
		type = string;
	}

	public void setQualifier(String string) {
		// TODO Auto-generated method stub
		qualifier = string;
	}

	public void setOpen(double parseDouble) {
		// TODO Auto-generated method stub
		open = parseDouble;
	}

	public void setHigh(double parseDouble) {
		// TODO Auto-generated method stub
		high = parseDouble;
	}

	public void setLow(double parseDouble) {
		// TODO Auto-generated method stub
		low = parseDouble;
	}

	public void setLast(double parseDouble) {
		// TODO Auto-generated method stub
		last = parseDouble;
	}

	public void setVolume(long parseLong) {
		// TODO Auto-generated method stub
		volume = parseLong;
	}

	public void setOpenInterest(double parseDouble) {
		// TODO Auto-generated method stub
		openInterest = parseDouble;
	}

	public void setSettle(String string) {
		// TODO Auto-generated method stub
		settle = string;
	}

	public void setDataSource(String string) {
		// TODO Auto-generated method stub
		dataSource = string;
	}
	public String getRic() {
		// TODO Auto-generated method stub
		return ric;
	}
	
	public Date getDate() {
		// TODO Auto-generated method stub
		return date;
	}

	public String getTime() {
		// TODO Auto-generated method stub
		return time;
	}

	public String getType() {
		// TODO Auto-generated method stub
		return type;
	}

	public String getQualifier() {
		// TODO Auto-generated method stub
		return qualifier;
	}

	public double getOpen() {
		// TODO Auto-generated method stub
		return open;
	}

	public double getHigh() {
		// TODO Auto-generated method stub
		return high;
	}

	public double getLow() {
		// TODO Auto-generated method stub
		return low;
	}

	public double getLast() {
		// TODO Auto-generated method stub
		return last;
	}

	public long getVolume() {
		// TODO Auto-generated method stub
		return volume;
	}

	public double getOpenInterest() {
		// TODO Auto-generated method stub
		return openInterest;
	}

	public String getSettle() {
		// TODO Auto-generated method stub
		return settle;
	}

	public String getDataSource() {
		// TODO Auto-generated method stub
		return dataSource;
	}
	
	
	
	
}
