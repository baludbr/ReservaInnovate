package com.klu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.klu.Entity.Booking;
import com.klu.Entity.Customer;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long>{
	@Query("SELECT c FROM Booking c WHERE c.CustomerID = :id")
	public List<Booking> getBookingDetailsById(@Param("id") String id);
}
