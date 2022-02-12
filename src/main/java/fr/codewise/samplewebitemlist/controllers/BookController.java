package fr.codewise.samplewebitemlist.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.codewise.samplewebitemlist.entities.Book;
import fr.codewise.samplewebitemlist.repositories.BookRepository;
import javassist.NotFoundException;

@RestController
@RequestMapping("/book")
public class BookController {

	@Autowired
	BookRepository bookRepository;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Book> getAllBookRecords() {
		return bookRepository.findAll();
	}

	@GetMapping("/{bookId}")
	public ResponseEntity<Book> getBookRecordById(@PathVariable("bookId") Long bookId) {
		return new ResponseEntity<> (bookRepository.findById(bookId).get(), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Book> createBookRecord(@RequestBody @Valid Book bookRecord) {

		return new ResponseEntity<Book>(bookRepository.save(bookRecord), HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<Book> updateBookRecord(@RequestBody @Valid Book bookRecord) throws NotFoundException {
		if (bookRecord == null || bookRecord.getBookId() == null) {
			throw new NotFoundException("Book record or ID must not be null");
		}

		Optional<Book> optionalBook = bookRepository.findById(bookRecord.getBookId());
		if (!optionalBook.isPresent()) {
			throw new NotFoundException("Book with ID: " + bookRecord.getBookId() + " does not exist.");
		}

		Book existingBookRecord = optionalBook.get();
		existingBookRecord.setName(bookRecord.getName());
		existingBookRecord.setSummary(bookRecord.getSummary());
		existingBookRecord.setRating(bookRecord.getRating());

		return new ResponseEntity<Book>(bookRepository.save(existingBookRecord), HttpStatus.OK);
	}

	@DeleteMapping("/{bookId}")
	public ResponseEntity<HttpStatus> deleteBook(@PathVariable("bookId") Long bookId) throws NotFoundException {
		
		if (!bookRepository.findById(bookId).isPresent()) {
			throw new NotFoundException("bookId " + bookId + " not present");
		}
		bookRepository.deleteById(bookId);
		
		return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
	}
	

}
