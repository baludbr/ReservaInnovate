package com.klu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.klu.Entity.Customer;
import com.klu.Entity.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room,Long>{
	@Query("SELECT c FROM Room c WHERE c.roomNo = :roomNo")
	public Room getBookingDetails(@Param("roomNo") String roomNo);
}
