package com.sekko.cinema;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.lang.String;
import com.sekko.cinema.Cinema;
import java.util.List;
 
public interface CinemaRepository extends JpaRepository<Cinema, Long>{
	@Query(value="select * from cinema  where location regexp ?1", nativeQuery=true)
	Iterable<Cinema> findByQu(String qu);
	
	//这种原生的查询比封装好的查询更快
	@Query(value="select * from cinema", nativeQuery=true)
	Iterable<Cinema> findAllCinema();
	
	Iterable<Cinema> findByCinemaId(String cinemaId);
	
	Cinema findById(long id);
	Cinema findByName(String name);
}
