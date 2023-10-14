package com.klu.Entity;

public class HotelInfo {
	private String hotelName;
    private String latitude;
    private String longitude;

    public HotelInfo(String hotelName, String latitude, String longitude) {
        this.hotelName = hotelName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getHotelName() {
        return hotelName;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return "HotelInfo{" +
                "hotelName='" + hotelName + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }
}
