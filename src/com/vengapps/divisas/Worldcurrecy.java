package com.vengapps.divisas;

public class Worldcurrecy {
	
	private String id, name , price , symbol , ts ,type ,utctime ,volume;

	
	
	
	
	
	
	public Worldcurrecy(String id, String name, String price, String symbol,
			String ts, String type, String utctime, String volume) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.symbol = symbol;
		this.ts = ts;
		this.type = type;
		this.utctime = utctime;
		this.volume = volume;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUtctime() {
		return utctime;
	}

	public void setUtctime(String utctime) {
		this.utctime = utctime;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}
		
	    
	    
	    
	    
	    
	    
}
