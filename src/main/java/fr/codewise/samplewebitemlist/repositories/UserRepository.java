package fr.codewise.samplewebitemlist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.codewise.samplewebitemlist.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	/*String findNameById(Long id);
	
	String findEmailById(Long Id);*/
}
