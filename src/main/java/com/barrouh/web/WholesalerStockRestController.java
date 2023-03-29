package com.barrouh.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.barrouh.dto.OrderDto;
import com.barrouh.dto.WholesalerStockDto;
import com.barrouh.service.WholesalerStockService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/wholesalers-stock")
public class WholesalerStockRestController {

	private final WholesalerStockService wholesalerStockService;

	/**
	 * FR4- Add the sale of an existing beer to an existing wholesaler.
	 * 
	 * @param wholesalerStock
	 * @return message
	 */
	@PostMapping("/save")
	public ResponseEntity<Object> saveWholesalerStock(@RequestBody WholesalerStockDto wholesalerStock) {
		return wholesalerStockService.addWholesalerStock(wholesalerStock);
	}

	/**
	 * FR5- A wholesaler can update the remaining quantity of a beer in his stock.
	 * 
	 * @param wholesalerStock
	 * @return message
	 */
	@PatchMapping("/update")
	public ResponseEntity<String> updateStock(@RequestBody WholesalerStockDto wholesalerStock) {
		return wholesalerStockService.updateWholesalerStock(wholesalerStock);
	}

	/**
	 * FR6- A client can request a quote from a wholesaler
	 * 
	 * @param order
	 * @return Order Summary or message
	 */
	@PostMapping("/order")
	public ResponseEntity<Object> order(@RequestBody OrderDto order) {
		return wholesalerStockService.makeOrder(order);
	}

}