package com.barrouh.service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.barrouh.dto.BeerDto;
import com.barrouh.model.Beer;
import com.barrouh.model.Brewery;
import com.barrouh.repository.BeerRepository;
import com.barrouh.util.MessagesKeys;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BeerService {

	private final ModelMapper mapper;

	private final MessageSource messageSource;

	private final BreweryService breweryService;

	private final BeerRepository beerRepository;

	/**
	 * find Beer by Id
	 * 
	 * @param beerId
	 * @return optional of
	 */
	public Optional<Beer> beerById(Integer beerId) {
		return beerRepository.findById(beerId);
	}

	/**
	 * FR1- List all the beers by brewery
	 * 
	 * @param breweryId
	 * @return list of beers
	 */
	public ResponseEntity<List<Beer>> beersByBrewery(Integer breweryId) {
		return new ResponseEntity<>(beerRepository.findByBreweryId(breweryId), HttpStatus.OK);
	}

	/**
	 * FR2- A brewer can add new beer.
	 * 
	 * @param beer
	 * @return created beer
	 */
	public ResponseEntity<Object> addBeer(BeerDto beer) {
		Optional<Brewery> brewery = breweryService.breweryById(beer.getBreweryId());
		if (!brewery.isPresent()) {
			Object[] args = new Object[] { beer.getBreweryId() };
			return new ResponseEntity<>(messageSource.getMessage(MessagesKeys.BREWERY_NOT_FOUND, args, Locale.ENGLISH),
					HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>(beerRepository.saveAndFlush(mapper.map(beer, Beer.class)), HttpStatus.CREATED);
	}

	/**
	 * FR3- A brewer can delete a beer.
	 * 
	 * @param beerId
	 * @return message
	 */
	public ResponseEntity<String> deleteBeerById(Integer beerId) {
		Object[] args = new Object[] { beerId };
		if (beerById(beerId).isPresent()) {
			beerRepository.deleteById(beerId);
			return new ResponseEntity<>(messageSource.getMessage(MessagesKeys.BEER_DELETED, args, Locale.ENGLISH),
					HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(messageSource.getMessage(MessagesKeys.BEER_NOT_FOUND, args, Locale.ENGLISH),
					HttpStatus.NOT_FOUND);
		}
	}

}