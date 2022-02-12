package fr.codewise.samplewebitemlist.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import fr.codewise.samplewebitemlist.entities.Item;

@Sql("/create-items.sql")
@SpringBootTest
public class ItemRepositoryIT {
	
	@Autowired
	ItemRepository itemRepository;
	
	
	@Test
	void findQuantityGreaterThanIT() {
		int quantity = 1;
		long expected = 3;
		
		List<Item> items = itemRepository.findByQuantityGreaterThan(quantity);
		assertEquals(expected, items.size());
	}
	
	/*@Test
	public void hello() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/hello");
		MvcResult result = mvc.perform(request).andReturn();
		assertEquals("Hello World", result.getResponse().getContentAsString());
	}*/
	
	/*@Test
	public void testHelloWithName() throws Exception {
		mvc.perform(get("/hello?name=Dan")).andExpect(content().string("Hello Dan"));
	}*/
}
