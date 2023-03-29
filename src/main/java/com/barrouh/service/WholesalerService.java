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

	public Optional<Wholesaler> wholesalerById(Integer beerId) {
		return wholesalerRepository.findById(beerId);
	}

}