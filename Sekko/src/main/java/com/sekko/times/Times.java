package com.sekko.times;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Times {
	@Id
	@NotNull
	private long id;
	
	@NotNull
	@Column(nullable=false, unique=true)
	private String timesId;
	
	@NotNull
	@Column(nullable=false, unique=true)
	private String date;
	
	@NotNull
	@Column(nullable=false, unique=true)
	private String cinemaId;
	
	@NotNull
	@Column(nullable=false, unique=true)
	private String movieId;
	
	@NotNull
	@Column(nullable=false, unique=true)
	private String time;
	
	@NotNull
	@Column(nullable=false, unique=true)
	private String languageAndEffect;
	
	@NotNull
	@Column(nullable=false, unique=true)
	private String playingRoom;
	
	@NotNull
	@Column(nullable=false, unique=true)
	private String price;
	
	public String getTimesId() {
		return timesId;
	}
	
	public void setTimesId(String timesId) {
		this.timesId = timesId;
	}
	
	public String getCinemaId() {
		return cinemaId;
	}
	
	public void setCinemaId(String cinemaId) {
		this.cinemaId = cinemaId;
	}
	
	public String getMovieId() {
		return movieId;
	}
	
	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public long getId() {
		return id;
	}
	
	public String getTime() {
		return time;
	}
	
	public String getLanguageAndEffect() {
		return languageAndEffect;
	}
	
	public String getPlayingRoom() {
		return playingRoom;
	}
	
	public String getPrice() {
		return price;
	}
}
