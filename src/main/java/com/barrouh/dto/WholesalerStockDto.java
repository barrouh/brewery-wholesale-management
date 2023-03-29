package com.barrouh.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class WholesalerStockDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer wholesalerId;
	private Integer beerId;
	private int quantity;

}