package com.yzzzzun.itemservice.web.form;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yzzzzun.itemservice.domain.item.DeliveryCode;
import com.yzzzzun.itemservice.domain.item.Item;
import com.yzzzzun.itemservice.domain.item.ItemRepository;
import com.yzzzzun.itemservice.domain.item.ItemType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/basic/items")
public class FormItemController {
	private final ItemRepository itemRepository;

	public FormItemController(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}

	@ModelAttribute("regions")
	public Map<String, String> regions(){
		Map<String, String> regions = new LinkedHashMap<>();
		regions.put("SEOUL","서울");
		regions.put("BUSAN","부산");
		regions.put("JEJU","제주");
		return regions;
	}

	@ModelAttribute("itemTypes")
	public ItemType[] itemTypes() {
		return ItemType.values();
	}

	@ModelAttribute("deliveryCodes")
	public List<DeliveryCode> deliveryCodes() {
		List<DeliveryCode> deliveryCodes = new ArrayList<>();
		deliveryCodes.add(new DeliveryCode("FAST","빠른배송"));
		deliveryCodes.add(new DeliveryCode("NORMAL","일반배송"));
		deliveryCodes.add(new DeliveryCode("SLOW","느린배송"));
		return deliveryCodes;
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
	public String addForm(Model model) {
		model.addAttribute("item", new Item());
		return "basic/addForm";
	}

	@PostMapping("/add")
	public String addItemV6(Item item, RedirectAttributes redirectAttributes){
		log.info("item.open={}", item.getOpen());
		log.info("item.regions={}", item.getRegions());
		log.info("item.itemType={}", item.getItemType());

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
