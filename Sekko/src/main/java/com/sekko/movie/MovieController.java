package com.sekko.movie;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javassist.expr.Instanceof;

@RestController
@RequestMapping("/movie")
public class MovieController {
	@Autowired
	MovieService movieService;
	
	//经过ab压力测试发现，getAllMovies()比listMovies()更速度更快
	@RequestMapping(value={"", "/"}, method = RequestMethod.GET)
	public Iterable<Movie> getMovie(@RequestParam(value = "type", defaultValue="") String type) {
		if (type.equals("")) {
			return movieService.getAllMovies();
		}
		return movieService.getMovieByType(type);
	}
	
	@RequestMapping(value={"/test"}, method = RequestMethod.GET)
	public Iterable<Movie> getAllMovie(@RequestParam(value = "type", defaultValue="") String type) {
		if (type.equals("")) {
			return movieService.listMovies();
		}
		return movieService.getMovieByType(type);
	}
}
