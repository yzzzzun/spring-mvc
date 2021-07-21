package com.yzzzzun.itemservice.domain.item;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ItemRepositoryTest {

	ItemRepository itemRepository = new ItemRepository();

	@AfterEach
	void afterEach(){
		itemRepository.clearStore();
	}

	@DisplayName("item 저장 테스트")
	@Test
	void testSave() {
	    //given
		Item item = new Item("itemA",10000,10);
	    //when
		Item savedItem = itemRepository.save(item);
	    //then
		Item findItem = itemRepository.findById(item.getId());
		assertThat(savedItem).isEqualTo(findItem);
	}

	@DisplayName("")
	@Test
	void testFindAll() {
	    //given
		Item itemA = new Item("itemA",10000,10);
		Item itemB = new Item("itemB",20000,20);
		itemRepository.save(itemA);
		itemRepository.save(itemB);
		//when
		List<Item> result = itemRepository.findAll();

		//then
		assertThat(result).containsExactly(itemA, itemB);
	}

	@DisplayName("")
	@Test
	void testUpdateItem() {
	    //given
		Item item = new Item("itemA",10000,10);
		Item savedItem = itemRepository.save(item);
		Long itemId = savedItem.getId();

		//when
		Item updateParam = new Item("itemB", 20000, 20);
		itemRepository.update(itemId, updateParam);
		Item findItem = itemRepository.findById(itemId);
		//then
		assertThat(findItem.getItemName()).isEqualTo(updateParam.getItemName());
		assertThat(findItem.getQuantity()).isEqualTo(updateParam.getQuantity());
		assertThat(findItem.getPrice()).isEqualTo(updateParam.getPrice());
	}


}