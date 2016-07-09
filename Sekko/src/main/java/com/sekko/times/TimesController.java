package com.sekko.times;

import java.awt.List;

import javax.validation.constraints.Null;

import com.sekko.times.Times;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/times")
public class TimesController {
	@Autowired
	TimesService timesService;
	
	@RequestMapping(value={"", "/"}, method = RequestMethod.GET)
	public Iterable<Times> getTimes(@RequestParam(value = "date", defaultValue="") String date, @RequestParam(value = "cinemaId", defaultValue="") String cinemaId, @RequestParam(value = "movieId", defaultValue="") String movieId) {
		return timesService.getTimesByThree(date, cinemaId, movieId);
	}
}
