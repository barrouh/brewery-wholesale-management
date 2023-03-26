package com.barrouh.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.barrouh.dto.BeerDto;
import com.barrouh.model.Beer;
import com.barrouh.repository.BeerRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BeerService {

	private final BeerRepository beerRepository;

	public List<Beer> getBeersByBrewery(Integer breweryId) {
		return beerRepository.findByBreweryId(breweryId);
	}

	public Beer addBeer(BeerDto beer) {
		return beerRepository.saveAndFlush(beer);
	}

	public Optional<Beer> beerById(Integer beerId) {
		return beerRepository.findById(beerId);
	}

	public void deleteBeerById(Integer beerId) {
		beerRepository.deleteById(beerId);
	}

}