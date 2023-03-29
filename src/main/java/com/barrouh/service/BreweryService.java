package com.barrouh.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.barrouh.model.Brewery;
import com.barrouh.repository.BreweryRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BreweryService {
	
	private final BreweryRepository breweryRepository;
	
	/**
	 * find Brewery by Id
	 * @param breweryId
	 * @return optional of Brewery
	 */
	public Optional<Brewery> breweryById(Integer breweryId) {
		return breweryRepository.findById(breweryId);
	}

}