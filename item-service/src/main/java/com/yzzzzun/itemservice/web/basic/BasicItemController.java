package com.yzzzzun.itemservice.web.basic;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yzzzzun.itemservice.domain.item.Item;
import com.yzzzzun.itemservice.domain.item.ItemRepository;

@Controller
@RequestMapping("/basic/items")
public class BasicItemController {
	private final ItemRepository itemRepository;

	public BasicItemController(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}

	@GetMapping
	public String items(Model model){
		List<Item> items = itemRepository.findAll();
		model.addAttribute("items", items);

		return "basic/items";
	}

	@PostConstruct
	public void init(){
		itemRepository.save(new Item("itemA", 10000, 10));
		itemRepository.save(new Item("itemB", 20000, 20));
	}
}
