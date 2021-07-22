package com.yzzzzun.itemservice.domain.item;

import java.util.List;

import lombok.Data;

@Data
public class Item {
	private Long id;
	private String itemName;
	private Integer price;
	private Integer quantity;

	private Boolean open;//퍈매여부
	private List<String> regions; //등록지역
	private ItemType itemType;
	private String deliveryCode;

	public Item() {
	}

	public Item(String itemName, Integer price, Integer quantity) {
		this.itemName = itemName;
		this.price = price;
		this.quantity = quantity;
	}
}
