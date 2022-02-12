package fr.codewise.samplewebitemlist.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import fr.codewise.samplewebitemlist.entities.Book;
import fr.codewise.samplewebitemlist.repositories.BookRepository;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BookInitializer implements CommandLineRunner {
	@Autowired
	BookRepository bookRepository;

	@Override
	public void run(String... args) throws Exception {
		Faker faker = new Faker();
		
		log.info("starting to initialize sample data....");
		/*for (int i = 0; i < 10; i++) {
			Book book = new Book();
			book.setName(faker.book().title());
			book.setSummary(faker.book().publisher());
			book.setRating(2);
			bookRepository.save(book);
		}*/
		
		log.info("Finished to initialize sample data....");
	}

}
