package fr.codewise.samplewebitemlist;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import fr.codewise.samplewebitemlist.controllers.ItemController;
import fr.codewise.samplewebitemlist.dto.CreateItemDto;
import fr.codewise.samplewebitemlist.entities.Item;
import fr.codewise.samplewebitemlist.services.ItemService;

@SpringBootTest
@AutoConfigureMockMvc
public class ItemControllerTest {
	@InjectMocks
	ItemController itemController;

	@Mock
	ItemService itemService;
	
	@Autowired
	private MockMvc mockMvc;
	private AutoCloseable closeable;

	ObjectMapper objectMapper = new ObjectMapper();
	ObjectWriter objectWriter = objectMapper.writer();
	
	Item item1 = new Item(1L, "Atomic Habits", 3);
	
	@BeforeEach
	public void setUp() {
		closeable = MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(itemController).build();
	}
	

	@Test
	public void test() {
		CreateItemDto dto = new CreateItemDto();
		dto.setName("Orange");
		dto.setQuantity(5);

		String result = itemController.createItem(dto);
		String expectedResult = "redirect:/";
		
		verify(itemService).createItem(any(CreateItemDto.class));
		assert(result).equals(expectedResult);

	}
	
	
	@Test
	public void getItemById_success() throws Exception {
		Item item2 = new Item(2L, "Atomic Habits", 3);
		when(itemService.findById(item2.getId())).thenReturn(item2);

		mockMvc.perform(MockMvcRequestBuilders.get("/item/2").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
				
	}

	@Test
	public void deleteItem() throws Exception {
		when(itemService.findById(item1.getId())).thenReturn(item1);

		mockMvc.perform(MockMvcRequestBuilders.delete("/item/{id}", 1L).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isAccepted());
	}
	
	@Test
	public void updateBook_success() throws Exception {
		Item updatedRecord = Item.builder().id(1L).name("Television").quantity(2).build();

		when(itemService.findById(item1.getId())).thenReturn(item1);

		when(itemService.updateItemById(updatedRecord)).thenReturn(updatedRecord);

		String updatedContent = objectWriter.writeValueAsString(updatedRecord);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/items")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(updatedContent);

		mockMvc.perform(mockRequest).andExpect(MockMvcResultMatchers.status().isOk());

	}
	
	@AfterEach
	public void closeService() throws Exception {
		closeable.close();
	}
}
