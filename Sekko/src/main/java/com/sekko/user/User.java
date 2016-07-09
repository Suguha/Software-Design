package com.sekko.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.apache.activemq.artemis.core.security.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;


@Entity
public class User implements UserDetails {

	public enum ROLE {
		admin,
		user
	};
	
	public enum GENDER {
		female,
		male,
		unknow
	};
	
	//如果枚举字段不加入@Enumerated, 数据库会按int类型处理
	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	private ROLE role;
	
	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	private GENDER gender;
	
	
	@NotNull
	@Column(nullable=false, unique=true, length=30)
	private String username;
	
	@NotNull
	@Column(nullable=false)
	private String password;
	
	@Column(length=30)
	private String nickname;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(length=128)
	private String avatarUrl;
	
	private String signature;
	
	//建立user与movie_comment和cinema_comment的联系
	// ....
	
	static public BCryptPasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder(4);
	}
	
	public User() {
		super();
		setRole(User.ROLE.user);
		setGender(User.GENDER.unknow);
		setNickname("sekkoer");
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}
	
	public ROLE getRole() {
		return role;
	}
	
	public void setRole(ROLE role) {
		this.role = role;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setPassword(String password) {
		this.password = getPasswordEncoder().encode(password);
	}
	
	public String getPassword() {
		return password;
	}

	
	public String getNickname() {
		return nickname;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getAvatarUrl() {
		return avatarUrl;
	}
	
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
	
	public String getSignature() {
		return signature;
	}
	
	public void setSignature(String signature) {
		this.signature = signature;
	}
	
	public void setGender(GENDER gender) {
		this.gender = gender;
	}
	
	public GENDER getGender() {
		return gender;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(getRole().name()));
		System.err.println("[" + username + ", " + nickname + ", " + getRole().name() + "]");
        return authorities;
	}


	@Override
	@JsonBackReference
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	@JsonBackReference
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	@JsonBackReference
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	@JsonBackReference
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
}
