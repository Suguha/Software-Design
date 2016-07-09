package com.sekko.cinema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cinema")
public class CinemaController {
	@Autowired
	CinemaService cinemaService;
	
	@RequestMapping(value={"", "/"}, method = RequestMethod.GET)
	public Iterable<Cinema> getCinema(@RequestParam(value = "qu", defaultValue="") String qu, @RequestParam(value = "cinemaId", defaultValue="") String cinemaId) {
		if (qu.equals("") && cinemaId.equals(""))
			return cinemaService.getAllCinema();
		if (!qu.equals(""))
			return cinemaService.getCinemaByQu(qu);
		return cinemaService.getCinemaByCinemaId(cinemaId);
	}
}
