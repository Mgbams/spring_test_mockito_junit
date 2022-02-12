package fr.codewise.samplewebitemlist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.codewise.samplewebitemlist.dto.BookDto;
import fr.codewise.samplewebitemlist.entities.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

	Long save(BookDto capture);

}
