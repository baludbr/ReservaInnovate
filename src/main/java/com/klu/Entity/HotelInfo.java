package com.klu.Entity;

public class HotelInfo {
	private String hotelName;
    private String latitude;
    private String longitude;
    private String description;
    public HotelInfo(String name, String latitude, String longitude, String description) {
        this.hotelName = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
    }
	public String getHotelName() {
		return hotelName;
	}
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
    
}
