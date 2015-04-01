package seng3011.msm;

import java.util.Date;

//UNSW CSE SENG3011 Team Awesome Copyright Reserved
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
		ric = string;
	}

	public void setDate(Date date2) {		
		date = date2;
	}

	public void setTime(String string) {		
		time = string;
	}

	public void setType(String string) {		
		type = string;
	}

	public void setQualifier(String string) {		
		qualifier = string;
	}

	public void setOpen(double parseDouble) {		
		open = parseDouble;
	}

	public void setHigh(double parseDouble) {		
		high = parseDouble;
	}

	public void setLow(double parseDouble) {		
		low = parseDouble;
	}

	public void setLast(double parseDouble) {		
		last = parseDouble;
	}

	public void setVolume(long parseLong) {		
		volume = parseLong;
	}

	public void setOpenInterest(double parseDouble) {		
		openInterest = parseDouble;
	}

	public void setSettle(String string) {		
		settle = string;
	}

	public void setDataSource(String string) {		
		dataSource = string;
	}
	public String getRic() {		
		return ric;
	}
	
	public Date getDate() {		
		return date;
	}

	public String getTime() {		
		return time;
	}

	public String getType() {		
		return type;
	}

	public String getQualifier() {		
		return qualifier;
	}

	public double getOpen() {		
		return open;
	}

	public double getHigh() {		
		return high;
	}

	public double getLow() {		
		return low;
	}

	public double getLast() {		
		return last;
	}

	public long getVolume() {		
		return volume;
	}

	public double getOpenInterest() {		
		return openInterest;
	}

	public String getSettle() {		
		return settle;
	}

	public String getDataSource() {		
		return dataSource;
	}

}
