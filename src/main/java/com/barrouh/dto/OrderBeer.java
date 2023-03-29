package com.barrouh.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class OrderBeer implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private float price;
	private Integer quantity;
}