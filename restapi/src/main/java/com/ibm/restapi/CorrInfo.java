package com.ibm.restapi;

public class CorrInfo {

	private String loc;
	private String zip;
	private String ts;
	
	public CorrInfo() {
		
	}
	
	public CorrInfo(String loc,String zip,String ts) {
		this.loc = loc;
		this.zip = zip;
		this.ts = ts;
	}
	public String getLoc() {
		return loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getTS() {
		return ts;
	}

	public void setTS(String ts) {
		this.ts = ts;
	}



	@Override
	public String toString() {
		return "CorrInfo [loc=" + loc + ", ts=" + ts + ", zip=" + zip + "]";
	}

}