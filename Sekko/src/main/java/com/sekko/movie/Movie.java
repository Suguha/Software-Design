package com.sekko.movie;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Movie {
	@NotNull
	@Column(nullable=false, unique=true)
	private String name;
	
	@NotNull
	@Column(nullable=false)
	private String img;
	
	@NotNull
	@Column(nullable=false)
	private String score;
	
	@NotNull
	@Column(nullable=false)
	private String type;
	
	private String timeAndLanguage; //查询数据库的时候驼峰命名会转为下划线
	
	private String description;
	
	private String onTime;
	
	private String actors;
	
	@Id
	@NotNull
	private long id;
	
	public long getId() {
		return id;
	}
	
	public String getActors() {
		return actors;
	}
	
	
	public String getOnTime() {
		return onTime;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getTimeAndLanguage() {
		return timeAndLanguage;
	}
	
	
	public String getType() {
		return type;
	}
	
	public String getScore() {
		return score;
	}
	
	
	public String getImg() {
		return img;
	}
	
	public String getName() {
		return name;
	}
	
}
