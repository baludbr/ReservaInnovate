package com.klu.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Room")
public class Hotel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long roomTypeID;
	String roomType;
	String pictureURL;
	String totalCapacity;
	String roomCapacity;
	String avaliability;
	String description;
	String price;
	public Long getRoomTypeID() {
		return roomTypeID;
	}
	public void setRoomTypeID(Long roomTypeID) {
		this.roomTypeID = roomTypeID;
	}
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	public String getPictureURL() {
		return pictureURL;
	}
	public void setPictureURL(String pictureURL) {
		this.pictureURL = pictureURL;
	}
	public String getTotalCapacity() {
		return totalCapacity;
	}
	public void setTotalCapacity(String totalCapacity) {
		this.totalCapacity = totalCapacity;
	}
	public String getRoomCapacity() {
		return roomCapacity;
	}
	public void setRoomCapacity(String roomCapacity) {
		this.roomCapacity = roomCapacity;
	}
	public String getAvaliability() {
		return avaliability;
	}
	public void setAvaliability(String avaliability) {
		this.avaliability = avaliability;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "Hotel [roomTypeID=" + roomTypeID + ", roomType=" + roomType + ", pictureURL=" + pictureURL
				+ ", totalCapacity=" + totalCapacity + ", roomCapacity=" + roomCapacity + ", avaliability="
				+ avaliability + ", description=" + description + ", price=" + price + "]";
	}
	
	
}