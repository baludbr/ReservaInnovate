package com.klu.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Events_hotel")
public class Events {
@Id
Long eventID;
String eventName;
String EventPlace;
String description;
public Long getEventID() {
	return eventID;
}
public void setEventID(Long eventID) {
	this.eventID = eventID;
}
public String getEventName() {
	return eventName;
}
public void setEventName(String eventName) {
	this.eventName = eventName;
}
public String getEventPlace() {
	return EventPlace;
}
public void setEventPlace(String eventPlace) {
	EventPlace = eventPlace;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
@Override
public String toString() {
	return "Events [eventID=" + eventID + ", eventName=" + eventName + ", EventPlace=" + EventPlace + ", description="
			+ description + "]";
}

}
