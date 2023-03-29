package com.barrouh.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

	/**
	 * FR1- List all the beers by brewery
	 * 
	 * @param breweryId
	 * @return list of beers
	 */
	@GetMapping("/{breweryId}")
	public ResponseEntity<List<Beer>> beersByBrewery(@PathVariable("breweryId") Integer breweryId) {
		return beerService.beersByBrewery(breweryId);
	}

	/**
	 * FR2- A brewer can add new beer.
	 * 
	 * @param beer
	 * @return created beer or message
	 */
	@PostMapping("/")
	public ResponseEntity<Object> addBeer(@RequestBody BeerDto beer) {
		return beerService.addBeer(beer);
	}

	/**
	 * FR3- A brewer can delete a beer.
	 * 
	 * @param beerId
	 * @return message
	 */
	@DeleteMapping("{beerId}")
	public ResponseEntity<String> deleteBeer(@PathVariable("beerId") Integer beerId) {
		return beerService.deleteBeerById(beerId);
	}

}