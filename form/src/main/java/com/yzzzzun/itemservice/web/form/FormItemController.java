package com.yzzzzun.itemservice.web.form;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yzzzzun.itemservice.domain.item.Item;
import com.yzzzzun.itemservice.domain.item.ItemRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/basic/items")
public class FormItemController {
	private final ItemRepository itemRepository;

	public FormItemController(ItemRepository itemRepository) {
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
	public String addForm(Model model)
	{
		model.addAttribute("item", new Item());
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

	//@PostMapping("/add")
	public String addItemV4(Item item){

		itemRepository.save(item);

		return "basic/item";
	}

	//@PostMapping("/add")
	public String addItemV5(Item item){

		itemRepository.save(item);

		return "redirect:/basic/items/" + item.getId();
	}

	@PostMapping("/add")
	public String addItemV6(Item item, RedirectAttributes redirectAttributes){
		log.info("item.open={}", item.getOpen());

		itemRepository.save(item);
		redirectAttributes.addAttribute("itemId",item.getId());
		redirectAttributes.addAttribute("status",true);
		return "redirect:/basic/items/{itemId}";
	}

	@GetMapping("/{itemId}/edit")
	public String editForm(@PathVariable("itemId") Long itemId, Model model){
		Item findItem = itemRepository.findById(itemId);
		model.addAttribute("item",findItem);
		return "basic/editForm";
	}

	@PostMapping("/{itemId}/edit")
	public String update(@PathVariable("itemId") Long itemId, @ModelAttribute Item item){
		itemRepository.update(itemId, item);
		return "redirect:/basic/items/{itemId}";
	}

	@PostConstruct
	public void init(){
		itemRepository.save(new Item("itemA", 10000, 10));
		itemRepository.save(new Item("itemB", 20000, 20));
	}
}
