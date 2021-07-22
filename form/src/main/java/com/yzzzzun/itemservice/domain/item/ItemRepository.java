package com.yzzzzun.itemservice.domain.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

@Repository
public class ItemRepository {

	private static final Map<Long, Item> store = new ConcurrentHashMap<>();
	private static Long sequance = 0L;

	public Item save(Item item){
		item.setId(++sequance);
		store.put(item.getId(), item);
		return item;
	}

	public Item findById(Long id){
		return store.get(id);
	}

	public List<Item> findAll(){
		return new ArrayList<>(store.values());
	}

	public void update(Long itemId, Item updateParam){
		Item findItem = findById(itemId);
		findItem.setItemName(updateParam.getItemName());
		findItem.setPrice(updateParam.getPrice());
		findItem.setQuantity(updateParam.getQuantity());
		findItem.setItemType(updateParam.getItemType());
		findItem.setDeliveryCode(updateParam.getDeliveryCode());
		findItem.setOpen(updateParam.getOpen());
		findItem.setRegions(updateParam.getRegions());
	}

	public void clearStore(){
		store.clear();
	}

}
