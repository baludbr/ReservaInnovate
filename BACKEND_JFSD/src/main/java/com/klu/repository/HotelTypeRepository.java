package com.klu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.klu.Entity.Hotel;

@Repository
public interface HotelTypeRepository extends JpaRepository<Hotel,Long>{

}
