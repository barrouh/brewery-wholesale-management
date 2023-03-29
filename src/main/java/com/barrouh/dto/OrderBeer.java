package com.barrouh.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class OrderBeer implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private BigDecimal price;
	private Integer quantity;
}