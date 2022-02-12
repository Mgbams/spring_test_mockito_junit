package fr.codewise.samplewebitemlist;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import fr.codewise.samplewebitemlist.dto.CreateItemDto;
import fr.codewise.samplewebitemlist.entities.Item;
import fr.codewise.samplewebitemlist.mappers.ItemMapper;
import fr.codewise.samplewebitemlist.repositories.ItemRepository;
import fr.codewise.samplewebitemlist.services.ItemService;

@ExtendWith(SpringExtension.class)
public class ItemServiceTest {

    @InjectMocks
    ItemService itemService;

    @Mock
    ItemRepository itemRepository;

    @Mock
    ItemMapper itemMapper;

    @Test
    public void test() {
        CreateItemDto dto = new CreateItemDto();
        dto.setName("Lait");
        dto.setQuantity(4);

        Item returnedItem = new Item();
        returnedItem.setName("Lait");
        returnedItem.setQuantity(4);

        when(itemMapper.mapCreateItemDtoToEntity(any()))
                .thenReturn(returnedItem);

        itemService.createItem(dto);

        verify(itemRepository).save(any(Item.class));
        verify(itemMapper).mapCreateItemDtoToEntity(any(CreateItemDto.class));
    }
}
