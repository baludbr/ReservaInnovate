package com.klu.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.klu.Entity.Hotel;

@Repository
public interface HotelTypeRepository extends JpaRepository<Hotel,Long>{
	@Query("SELECT c FROM Hotel c WHERE c.roomType = :roomType")
	public Hotel getRoomType(@Param("roomType") String roomType);
}
