package com.example.model;

public class DemStats {
	String monthName;
	int monthNumber;
	int value;
	
	public DemStats(String monthName, int monthNumber, int value) {
		super();
		this.monthName = monthName;
		this.monthNumber = monthNumber;
		this.value = value;
	}
	
	
	public String getMonthName() {
		return monthName;
	}
	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}
	public int getMonthNumber() {
		return monthNumber;
	}
	public void setMonthNumber(int monthNumber) {
		this.monthNumber = monthNumber;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	
	
	
			
	
			
}
