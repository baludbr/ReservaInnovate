package com.klu.Entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Room_details")
public class Room {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long roomID;
	String roomType;
	String roomNo;
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	String availabilty="Not Booked";
	public Long getRoomID() {
		return roomID;
	}
	public void setRoomID(Long roomID) {
		this.roomID = roomID;
	}
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	public String getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}
	public String getAvailabilty() {
		return availabilty;
	}
	public void setAvailabilty(String availabilty) {
		this.availabilty = availabilty;
	}
	@Override
	public String toString() {
		return "Room [roomID=" + roomID + ", roomType=" + roomType + ", roomNo=" + roomNo + ", availabilty="
				+ availabilty + "]";
	}
	
}