package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Image;



@Repository
public interface ImageRepository extends JpaRepository<Image, Long>{
	
    Optional<Image> findByName(String name);
    Image getById(long id);
    List<Image> findByStatus(String status);
    
 	@Modifying()
	@Transactional
	@Query(value ="UPDATE Image i SET i.status=:newStatus where i.id=:id")
 	int updateStatus(@Param("newStatus") String newStatus,@Param("id") long id);
 	
 	
    

}
