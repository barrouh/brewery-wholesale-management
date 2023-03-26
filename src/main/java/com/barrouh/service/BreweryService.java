package com.barrouh.service;

import org.springframework.stereotype.Service;

import com.barrouh.repository.BreweryRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BreweryService {
	
	private final BreweryRepository breweryRepository;

}