package com.barrouh.web;

import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.barrouh.dto.BeerDto;
import com.barrouh.model.Beer;
import com.barrouh.service.BeerService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/beers")
public class BeerRestController {

	private final BeerService beerService;

	private final MessageSource messageSource;

	/**
	 * FR1- List all the beers by brewery
	 * 
	 * @param breweryId
	 * @return list of beers
	 */
	@GetMapping("/{breweryId}")
	public ResponseEntity<List<Beer>> beersByBrewery(@PathVariable("breweryId") Integer breweryId) {
		return new ResponseEntity<>(beerService.getBeersByBrewery(breweryId), HttpStatus.OK);
	}

	/**
	 * FR2- A brewer can add new beer.
	 * 
	 * @param beer
	 * @return created beer
	 */
	@PostMapping("/")
	public ResponseEntity<Beer> addBeer(@RequestBody BeerDto beer) {
		return new ResponseEntity<>(beerService.addBeer(beer), HttpStatus.CREATED);
	}

	/**
	 * FR3- A brewer can delete a beer.
	 * 
	 * @param beerId
	 * @return message
	 */
	@PostMapping("{breweryId}")
	public ResponseEntity<String> deleteBeer(@PathVariable("breweryId") Integer beerId) {
		Object[] args = new Object[] { beerId };
		if (beerService.beerById(beerId).isPresent()) {
			beerService.deleteBeerById(beerId);
			return new ResponseEntity<>(messageSource.getMessage("beer.deleted", args, Locale.ENGLISH),
					HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(messageSource.getMessage("beer.not.found", args, Locale.ENGLISH),
					HttpStatus.NOT_FOUND);
		}
	}

}