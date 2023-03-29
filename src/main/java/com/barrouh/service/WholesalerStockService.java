package com.barrouh.service;

import java.util.Locale;
import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.barrouh.dto.WholesalerStockDto;
import com.barrouh.model.Beer;
import com.barrouh.model.Wholesaler;
import com.barrouh.model.WholesalerStock;
import com.barrouh.model.WholesalerStockId;
import com.barrouh.repository.WholesalerStockRepository;
import com.barrouh.util.MessagesKeys;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class WholesalerStockService {

	private final BeerService beerService;

	private final MessageSource messageSource;

	private final WholesalerService wholesalerService;

	private final WholesalerStockRepository wholesalerStockRepository;

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

		if (!wholesaler.isPresent()) {
			args = new Object[] { wsDto.getWholesalerId() };
			return new ResponseEntity<>(
					messageSource.getMessage(MessagesKeys.WHOLESALER_NOT_FOUND, args, Locale.ENGLISH),
					HttpStatus.NOT_FOUND);
		}

		if (!beer.isPresent()) {
			args = new Object[] { wsDto.getBeerId() };
			return new ResponseEntity<>(messageSource.getMessage(MessagesKeys.BEER_NOT_FOUND, args, Locale.ENGLISH),
					HttpStatus.NOT_FOUND);
		}

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

}