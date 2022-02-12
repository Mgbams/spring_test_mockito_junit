package fr.codewise.samplewebitemlist.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.codewise.samplewebitemlist.dto.CreateItemDto;
import fr.codewise.samplewebitemlist.entities.Book;
import fr.codewise.samplewebitemlist.entities.Item;
import fr.codewise.samplewebitemlist.services.ItemService;
import javassist.NotFoundException;

@RestController
public class ItemController {

    @Autowired
    ItemService itemService;

    @GetMapping("/")
    public String getItemsPage(Model model) {
        model.addAttribute("items", itemService.findAllItems());
        return "items";
    }

    @PostMapping("/items")
    public String createItem(CreateItemDto dto) {
        itemService.createItem(dto);
        return "redirect:/";
    }
    
    /*@PutMapping("/item/{id}")
    public String updateItem(@PathVariable Long id) {
        itemService.updateItemById(id);
        return "Update..done";
    }*/
    
	@PutMapping("/items")
	public ResponseEntity<Item> updateItemRecord(@RequestBody @Valid Item itemRecord) throws RuntimeException {
		
		Item optionalItem = null;
		
		try {
			optionalItem  = itemService.findById(itemRecord.getId());
			optionalItem.setName(itemRecord.getName());
			optionalItem.setQuantity(itemRecord.getQuantity());
			itemService.updateItemById(optionalItem);
		} catch(RuntimeException e) {
			System.out.println(e.getMessage());
		}

		return new ResponseEntity<Item>(optionalItem, HttpStatus.OK);
	}
    
    @DeleteMapping("/item/{id}")
    public ResponseEntity<HttpStatus> deleteItem(@PathVariable("id") Long id) {
		
		try {
			itemService.deleteItemById(id);
		} catch(RuntimeException e) {
			System.out.println(e.getMessage());
		}
		
		return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
    }
    
    @GetMapping("/item/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable("id") Long id) {
    	Item gotItem = null;
    	try {
			gotItem = itemService.findById(id);
		} catch(RuntimeException e) {
			System.out.println(e.getMessage());
		}
        return new ResponseEntity<> (gotItem, HttpStatus.OK);
    }
    
    
}
