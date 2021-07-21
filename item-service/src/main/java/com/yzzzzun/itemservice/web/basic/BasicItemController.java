package com.yzzzzun.itemservice.web.basic;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

	@GetMapping("/{itemId}")
	public String item(@PathVariable("itemId") Long itemId, Model model){
		Item findItem = itemRepository.findById(itemId);
		model.addAttribute("item",findItem);
		return "basic/item";
	}

	@GetMapping("/add")
	public String addForm(){
		return "basic/addForm";
	}

	// @PostMapping("/add")
	public String addItemV1(@RequestParam String itemName,
		@RequestParam int price,
		@RequestParam Integer quantity,
		Model model){

		Item item = new Item(itemName,price,quantity);
		itemRepository.save(item);

		model.addAttribute("item",item);

		return "basic/item";
	}

	// @PostMapping("/add")
	public String addItemV2(@ModelAttribute("item") Item item,
		Model model){

		itemRepository.save(item);

		// @ModelAttribute 의 "item" 설정이 자동 추가해준다.
		//model.addAttribute("item",item);

		return "basic/item";
	}

	// @PostMapping("/add")
	public String addItemV3(@ModelAttribute Item item,
		Model model){

		itemRepository.save(item);

		// name설정을 안하면 클래스명의 첫글자를 소문자로 변경하여 addAttribute한다.
		//model.addAttribute("item",item);

		return "basic/item";
	}

	@PostMapping("/add")
	public String addItemV4(Item item){

		itemRepository.save(item);

		return "basic/item";
	}

	@PostConstruct
	public void init(){
		itemRepository.save(new Item("itemA", 10000, 10));
		itemRepository.save(new Item("itemB", 20000, 20));
	}
}
