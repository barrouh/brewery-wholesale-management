package com.barrouh.service;

import org.springframework.stereotype.Service;

import com.barrouh.repository.WholesalerStockRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class WholesalerStockService {
	
	private final WholesalerStockRepository wholesalerStockRepository;

}