package fr.codewise.samplewebitemlist.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.codewise.samplewebitemlist.dto.CreateItemDto;
import fr.codewise.samplewebitemlist.entities.Item;
import fr.codewise.samplewebitemlist.mappers.ItemMapper;
import fr.codewise.samplewebitemlist.repositories.ItemRepository;

@Service
public class ItemService {

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	ItemMapper itemMapper;

	public List<Item> findAllItems() {
		return itemRepository.findAll();
	}

	/**
	 * Méthode de création d'item
	 * 
	 * @param dto objet représentant un Item
	 */
	public void createItem(CreateItemDto dto) {
		itemRepository.save(itemMapper.mapCreateItemDtoToEntity(dto));
	}

	/*
	 * 2) Créez une feature deleteItemById, findById et updateItem 3) Testez le
	 * service et le controller de ces 3 features
	 * 
	 */
	
	public void deleteItemById(Long id) {
		Optional<Item> item = itemRepository.findById(id);
		// Item item = this.findById(id);
		itemRepository.delete(item.orElseThrow());
	}
	
	public Item findById(Long id) {
		Optional<Item>  optional = itemRepository.findById(id);
		Item item = null;
		
		if (optional.isPresent()) {
			item = optional.get();
		} else {
			throw new RuntimeException("Item not found for id :: " + id);
		}
		return item;
	}
	
	public Item updateItemById(Item item) {
		Item itemUpdate = item;
		if (itemUpdate != null) {
		  itemRepository.save(item);
		}
		
		return itemUpdate;
	}
	

}
