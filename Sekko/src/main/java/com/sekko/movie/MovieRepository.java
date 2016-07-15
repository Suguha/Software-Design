package com.sekko.movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MovieRepository  extends JpaRepository<Movie, Long>{
	//根据类新选择电影
	@Query(value="select * from movie  where type regexp ?1", nativeQuery=true)
	Iterable<Movie> findByType(String type);
	
	//这种原生的查询比封装好的查询更快
	@Query(value="select * from movie", nativeQuery=true)
	Iterable<Movie> findAllMovie();
	
	Movie findById(long id);
	Movie findByName(String name);
}
