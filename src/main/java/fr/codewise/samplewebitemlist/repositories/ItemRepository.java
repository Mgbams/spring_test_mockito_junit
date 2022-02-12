package fr.codewise.samplewebitemlist.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import fr.codewise.samplewebitemlist.entities.Item;

@Component
public interface ItemRepository extends JpaRepository<Item, Long> {
	List<Item> findByQuantityGreaterThan(Integer quantity);

	List<Item> findByName(String name);
}
