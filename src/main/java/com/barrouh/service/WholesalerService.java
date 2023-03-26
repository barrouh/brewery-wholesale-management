package com.barrouh.service;

import org.springframework.stereotype.Service;

import com.barrouh.repository.WholesalerRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class WholesalerService {
	
	private final WholesalerRepository wholesalerRepository;

}