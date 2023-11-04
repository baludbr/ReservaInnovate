package com.klu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.klu.Entity.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room,Long>{

}
