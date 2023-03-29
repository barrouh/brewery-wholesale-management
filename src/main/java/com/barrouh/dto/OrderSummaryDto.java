package com.barrouh.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.barrouh.model.Wholesaler;

import lombok.Data;

@Data
public class OrderSummaryDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Wholesaler wholesaler;
	private BigDecimal total;
	private BigDecimal totalAfterDiscount;
	private int totalQuantity;
	private List<OrderBeer> beers;

	public void addBeer(OrderBeer beer) {
		if (beers == null) {
			beers = new ArrayList<>();
		}
		beers.add(beer);
	}

}