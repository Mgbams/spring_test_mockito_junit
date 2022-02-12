package fr.codewise.samplewebitemlist.controller;

import static org.mockito.Mockito.when;

import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import fr.codewise.samplewebitemlist.controllers.BookController;
import fr.codewise.samplewebitemlist.entities.Book;
import fr.codewise.samplewebitemlist.repositories.BookRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerIntTest {

	@Autowired
	private MockMvc mockMvc;
	private AutoCloseable closeable;

	ObjectMapper objectMapper = new ObjectMapper();
	ObjectWriter objectWriter = objectMapper.writer();

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private BookController bookController;

	List<Book> records = null;

	@BeforeEach
	public void setUp() {
		closeable = MockitoAnnotations.openMocks(this);
		// given -setup or precondition
		records = List.of(Book.builder().name("Spider Man").rating(4).summary("A day of war").build(),
				Book.builder().name("Avengers").rating(2).summary("The reckoning").build());
		
		bookRepository.saveAll(records);
	}

	@Test
	@DisplayName("Using @Autowired during integration test")
	public void getAllRecords_success2() throws Exception {

		// When - action
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/book"));

		// then -verify output
		response.andExpect(MockMvcResultMatchers.status().isOk());
		response.andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(records.size())));
		response.andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Avengers"));
		response.andExpect(MockMvcResultMatchers.jsonPath("$[1].rating").value(2));
	}

	@Test
	public void getBookById_success() throws Exception {

		// When - action
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/book/1"));

		// then -verify output
		response.andExpect(MockMvcResultMatchers.status().isOk());
		response.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Spider Man"));
		response.andExpect(MockMvcResultMatchers.jsonPath("$.rating").value(4));
	}

	@AfterEach
	public void closeService() throws Exception {
		closeable.close();
	}

}
