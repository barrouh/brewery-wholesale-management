package com.barrouh.service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.barrouh.dto.OrderBeer;
import com.barrouh.dto.OrderDto;
import com.barrouh.dto.OrderSummaryDto;
import com.barrouh.dto.WholesalerStockDto;
import com.barrouh.model.Beer;
import com.barrouh.model.Wholesaler;
import com.barrouh.model.WholesalerStock;
import com.barrouh.model.WholesalerStockId;
import com.barrouh.repository.WholesalerStockRepository;
import com.barrouh.util.MessagesKeys;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WholesalerStockService {

	private final ModelMapper mapper;

	private final BeerService beerService;

	private final MessageSource messageSource;

	private final WholesalerService wholesalerService;

	private final WholesalerStockRepository wholesalerStockRepository;

	@Value("${discount.applied.above.10.drinks}")
	private Integer discountAbove10;

	@Value("${discount.applied.above.20.drinks}")
	private Integer discountAbove20;

	/**
	 * find WholesalerStock by Id
	 * 
	 * @param id
	 * @return optional of WholesalerStock
	 */
	public Optional<WholesalerStock> wholesalerStockById(WholesalerStockId id) {
		return wholesalerStockRepository.findById(id);
	}

	/**
	 * FR4- Add the sale of an existing beer to an existing wholesaler.
	 * 
	 * @param wholesalerStock
	 * @return created WholesalerStock or message
	 */
	public ResponseEntity<Object> addWholesalerStock(WholesalerStockDto wsDto) {
		WholesalerStockId id = new WholesalerStockId(wsDto.getWholesalerId(), wsDto.getBeerId());
		Object[] args;
		Optional<Wholesaler> wholesaler = wholesalerService.wholesalerById(wsDto.getWholesalerId());
		Optional<Beer> beer = beerService.beerById(wsDto.getBeerId());
		// check if Wholesaler is not exist
		if (!wholesaler.isPresent()) {
			args = new Object[] { wsDto.getWholesalerId() };
			return new ResponseEntity<>(
					messageSource.getMessage(MessagesKeys.WHOLESALER_NOT_FOUND, args, Locale.ENGLISH),
					HttpStatus.NOT_FOUND);
		}
		// check if Beer is not exist
		if (!beer.isPresent()) {
			args = new Object[] { wsDto.getBeerId() };
			return new ResponseEntity<>(messageSource.getMessage(MessagesKeys.BEER_NOT_FOUND, args, Locale.ENGLISH),
					HttpStatus.NOT_FOUND);
		}

		// update WholesalerStock
		WholesalerStock wholesalerStock = wholesalerStockRepository
				.saveAndFlush(new WholesalerStock(id, wsDto.getQuantity()));
		return new ResponseEntity<>(wholesalerStock, HttpStatus.CREATED);
	}

	/**
	 * FR5- A wholesaler can update the remaining quantity of a beer in his stock.
	 * 
	 * @param wholesalerStock
	 * @return message
	 */
	public ResponseEntity<String> updateWholesalerStock(WholesalerStockDto wholesalerStock) {
		WholesalerStockId id = new WholesalerStockId(wholesalerStock.getWholesalerId(), wholesalerStock.getBeerId());
		Object[] args = new Object[] { id };
		Optional<WholesalerStock> wholesalerStockOp = wholesalerStockById(id);
		// check if WholesalerStock is not exist
		if (wholesalerStockOp.isPresent()) {
			wholesalerStockRepository.save(new WholesalerStock(id, wholesalerStock.getQuantity()));
			return new ResponseEntity<>(
					messageSource.getMessage(MessagesKeys.WHOLESALERSTOCK_UPDATED, args, Locale.ENGLISH),
					HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(
					messageSource.getMessage(MessagesKeys.WHOLESALERSTOCK_NOT_FOUND, args, Locale.ENGLISH),
					HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * FR6- A client can request a quote from a wholesaler
	 * 
	 * @param order
	 * @return Order Summary or message
	 */
	public ResponseEntity<Object> makeOrder(OrderDto order) {
		// check if Order is empty
		if (isOrderEmpty(order)) {
			return new ResponseEntity<>(messageSource.getMessage(MessagesKeys.ORDER_EMPTY, null, Locale.ENGLISH),
					HttpStatus.NOT_FOUND);
		}
		// check if Order is duplicated
		if (isOrderDuplicated(order.getBeers())) {
			return new ResponseEntity<>(messageSource.getMessage(MessagesKeys.ORDER_EMPTY, null, Locale.ENGLISH),
					HttpStatus.NOT_FOUND);
		}
		// check if Wholesaler is not exist
		Optional<Wholesaler> wholesaler = wholesalerService.wholesalerById(order.getWholesalerId());
		if (!wholesaler.isPresent()) {
			return new ResponseEntity<>(
					messageSource.getMessage(MessagesKeys.WHOLESALER_MUST_EXIST, null, Locale.ENGLISH),
					HttpStatus.NOT_FOUND);
		}

		OrderSummaryDto orderSummary = new OrderSummaryDto();
		orderSummary.setWholesaler(wholesaler.get());
		for (OrderBeer beer : order.getBeers()) {
			WholesalerStockId id = new WholesalerStockId(order.getWholesalerId(), beer.getId());
			Optional<WholesalerStock> wholesalerStock = wholesalerStockById(id);
			// check if WholesalerStock is not exist
			if (!wholesalerStock.isPresent()) {
				return new ResponseEntity<>(
						messageSource.getMessage(MessagesKeys.BEER_NOT_BELONG_WHOLESALER, null, Locale.ENGLISH),
						HttpStatus.NOT_FOUND);
				// check quantity in WholesalerStock and Order
			} else if (wholesalerStock.get().getQuantity() < beer.getQuantity()) {
				return new ResponseEntity<>(
						messageSource.getMessage(MessagesKeys.STOCK_INSUFFICIENT, null, Locale.ENGLISH),
						HttpStatus.NOT_FOUND);
			}
			Optional<Beer> ops = beerService.beerById(beer.getId());
			if (ops.isPresent()) {
				OrderBeer tmp = mapper.map(ops.get(), OrderBeer.class);
				tmp.setQuantity(beer.getQuantity());
				orderSummary.addBeer(tmp);
			}
		}

		calculatePrice(orderSummary);
		return new ResponseEntity<>(orderSummary, HttpStatus.OK);
	}

	private void calculatePrice(OrderSummaryDto orderSummary) {
		BigDecimal total = new BigDecimal(0);
		int totalQuantity = 0;
		for (OrderBeer beer : orderSummary.getBeers()) {
			// calculate total price in order
			total = total.add(beer.getPrice().multiply(BigDecimal.valueOf(beer.getQuantity())));
			// calculate total beers in order
			totalQuantity += beer.getQuantity();
			// update stock
			WholesalerStockId id = new WholesalerStockId(orderSummary.getWholesaler().getId(), beer.getId());
			Optional<WholesalerStock> ops = wholesalerStockById(id);
			if (ops.isPresent()) {
				WholesalerStock wholesalerStock = ops.get();
				wholesalerStock.setQuantity(wholesalerStock.getQuantity() - beer.getQuantity());
				wholesalerStockRepository.save(wholesalerStock);
			}

		}
		orderSummary.setTotalQuantity(totalQuantity);
		orderSummary.setTotal(total);
		// calculate discount
		calculateDiscount(orderSummary);

	}

	private void calculateDiscount(OrderSummaryDto orderSummary) {
		BigDecimal totalAfterDiscount = orderSummary.getTotal();
		BigDecimal discount;
		if (orderSummary.getTotalQuantity() > discountAbove20) {
			discount = totalAfterDiscount.multiply(BigDecimal.valueOf(discountAbove20).divide(BigDecimal.valueOf(100)));
			totalAfterDiscount = totalAfterDiscount.subtract(discount);
		} else if (orderSummary.getTotalQuantity() > discountAbove10) {
			discount = totalAfterDiscount.multiply(BigDecimal.valueOf(discountAbove10).divide(BigDecimal.valueOf(100)));
			totalAfterDiscount = totalAfterDiscount.subtract(discount);
		}
		orderSummary.setTotalAfterDiscount(totalAfterDiscount);
	}

	private boolean isOrderDuplicated(List<OrderBeer> beers) {
		Set<Integer> set = new HashSet<>();
		for (OrderBeer beer : beers) {
			if (set.contains(beer.getId())) {
				return true;
			} else {
				set.add(beer.getId());
			}
		}
		return false;
	}

	private boolean isOrderEmpty(OrderDto order) {
		return order.getWholesalerId() == null || order.getBeers().isEmpty();
	}

}