package com.sekko.times;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.lang.String;
import com.sekko.times.Times;
import java.util.List;
 
public interface TimesRepository extends JpaRepository<Times, Long>{
	
	//这种原生的查询比封装好的查询更快
	@Query(value="select * from times", nativeQuery=true)
	Iterable<Times> findAllTimes();
	
	@Query(value="select * from times where movie_id = ?3 and date = ?1 and cinema_id = ?2", nativeQuery=true)
	Iterable<Times> findByThree(String date, String cinemaId, String movieId);
	
	List<Times> findByTimesId(String timesid);
	
	Times findById(long id);
}
