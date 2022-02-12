package fr.codewise.samplewebitemlist;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {
	
	/*@Mock
	private UserRepository userRepository;
	
	private AutoCloseable closeable;

	@InjectMocks
	private UserService userService;
	
	@BeforeEach
	public void setUp() {
		closeable = MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void testGetNameById() {
		when(userRepository.findNameById(101L)).thenReturn("Nick");
		
		String name = userService.getNameByUserId(101L);
		assertEquals("Nick", name);
	}*/
}
