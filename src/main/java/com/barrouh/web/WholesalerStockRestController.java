package com.barrouh.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.barrouh.service.WholesalerStockService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/wholesalers-stock")
public class WholesalerStockRestController {
	
	private final WholesalerStockService wholesalerStockService;

}