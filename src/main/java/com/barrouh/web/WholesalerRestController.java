package com.barrouh.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.barrouh.service.WholesalerService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/wholesalers")
public class WholesalerRestController {
	
	private final WholesalerService wholesalerService;

}