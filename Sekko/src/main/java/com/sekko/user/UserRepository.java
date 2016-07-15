package com.sekko.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
	@Query("select u from User u where u.username=?1 and u.password=?2")
	User login(String email, String password);
	
	User findByUsernameAndPassword(String username, String password);
	
	User findByUsername(String username);  
}
