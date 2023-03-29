package com.barrouh.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class BeerDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	private float alcoholPercentage;
	private BigDecimal price;
	private Integer breweryId;

}