package com.sekko.cinema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CinemaService {
	@Autowired
	private CinemaRepository cinemaRepository;
	
	public CinemaService() {
		super();
	}
	
	public Iterable<Cinema> listCinema() {
		return cinemaRepository.findAll();
	}
	
	public Cinema getCinemaById(long id) {
		return cinemaRepository.findById(id);
	}
	
	//效率更高
	public Iterable<Cinema> getAllCinema() {
		return cinemaRepository.findAllCinema();
	}
	
	public Iterable<Cinema> getCinemaByQu(String qu) {
		return cinemaRepository.findByQu(qu);
	}
	
	public Iterable<Cinema> getCinemaByCinemaId(String cinemaId) {
		return cinemaRepository.findByCinemaId(cinemaId);
	}
}
