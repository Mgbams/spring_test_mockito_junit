package fr.codewise.samplewebitemlist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import fr.codewise.samplewebitemlist.controllers.BookController;
import fr.codewise.samplewebitemlist.entities.Book;
import fr.codewise.samplewebitemlist.repositories.BookRepository;

@ExtendWith(SpringExtension.class)
public class BookControllerTest {

	private MockMvc mockMvc;
	private AutoCloseable closeable;

	private ObjectMapper objectMapper = new ObjectMapper();
	private ObjectWriter objectWriter = objectMapper.writer();

	@Mock
	private BookRepository bookRepository;

	@InjectMocks
	private BookController bookController;

	@Captor
	private ArgumentCaptor<Book> argumentCaptor;

	Book RECORD_1 = new Book(1L, "Atomic Habits", "How to build better better Habits", 3);
	Book RECORD_2 = new Book(2L, "Thinking Fast and slow", "How to create good mental methods", 4);
	Book RECORD_3 = new Book(3L, "Groking Algorithms", "Learn algorithms the fun way", 2);

	@BeforeEach
	public void setUp() {
		closeable = MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
	}

	@Test
	public void createBook2_success() throws Exception {
		Book record = new Book();
		record.setName("Trial One");
		record.setRating(3);
		record.setSummary("A wonderful world");

		when(bookRepository.save(argumentCaptor.capture())).thenReturn(record);

		String content = objectWriter.writeValueAsString(record);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/book")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content);

		mockMvc.perform(mockRequest).andExpect(MockMvcResultMatchers.status().isCreated());

		assertEquals("Trial One", argumentCaptor.getValue().getName());
		assertEquals(3, argumentCaptor.getValue().getRating());
		assertEquals("A wonderful world", argumentCaptor.getValue().getSummary());
	}

	@Test
	public void getAllRecords_success() throws Exception {
		List<Book> records = new ArrayList<>(Arrays.asList(RECORD_1, RECORD_2, RECORD_3));
		when(bookRepository.findAll()).thenReturn(records);

		mockMvc.perform(MockMvcRequestBuilders.get("/book").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(3))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Thinking Fast and slow"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].rating").value(4));
	}

	@Test
	public void getBookById_success() throws Exception {
		when(bookRepository.findById(RECORD_1.getBookId())).thenReturn(java.util.Optional.of(RECORD_1));

		mockMvc.perform(MockMvcRequestBuilders.get("/book/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.bookId").isNotEmpty())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Atomic Habits"));
	}

	@Test
	public void deleteBook() throws Exception {
		when(bookRepository.findById(RECORD_1.getBookId())).thenReturn(Optional.of(RECORD_1));

		mockMvc.perform(MockMvcRequestBuilders.delete("/book/{bookId}", 1L).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isAccepted());
	}

	// Use of TDD method
	@Test
	public void deleteBookById_success() throws Exception {
		when(bookRepository.findById(RECORD_2.getBookId())).thenReturn(Optional.of(RECORD_2));

		mockMvc.perform(MockMvcRequestBuilders.delete("/book/{bookId}", 2L).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isAccepted());

	}

	@Test
	public void updateBook_success() throws Exception {
		Book updatedRecord = Book.builder().bookId(1L).name("Update Book name").summary("Update Summary").rating(1)
				.build();

		when(bookRepository.findById(RECORD_1.getBookId())).thenReturn(java.util.Optional.ofNullable(RECORD_1));

		when(bookRepository.save(updatedRecord)).thenReturn(updatedRecord);

		String updatedContent = objectWriter.writeValueAsString(updatedRecord);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/book")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(updatedContent);

		mockMvc.perform(mockRequest).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Update Book name"));

	}

	@Test
	public void createBook_success() throws Exception {
		Book record = Book.builder().bookId(4L).name("Introduction to Mockito").summary("Learning as fast as i can")
				.rating(2).build();
		when(bookRepository.save(record)).thenReturn(record);

		String content = objectWriter.writeValueAsString(record);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/book")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content);

		mockMvc.perform(mockRequest).andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Introduction to Mockito"));

	}

	@AfterEach
	public void closeService() throws Exception {
		closeable.close();
	}

}
