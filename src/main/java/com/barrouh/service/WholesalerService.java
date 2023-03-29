package com.barrouh.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.barrouh.model.Wholesaler;
import com.barrouh.repository.WholesalerRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class WholesalerService {

	private final WholesalerRepository wholesalerRepository;

	/**
	 * find Wholesaler by Id
	 * 
	 * @param beerId
	 * @return optional of Wholesaler
	 */
	public Optional<Wholesaler> wholesalerById(Integer wholesalerId) {
		return wholesalerRepository.findById(wholesalerId);
	}

}