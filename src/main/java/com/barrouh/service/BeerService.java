package com.barrouh.service;

import org.springframework.stereotype.Service;

import com.barrouh.repository.BeerRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BeerService {
	
	private final BeerRepository beerRepository;

}