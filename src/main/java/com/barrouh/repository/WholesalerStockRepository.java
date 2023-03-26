package com.barrouh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.barrouh.model.WholesalerStock;

@Repository
public interface WholesalerStockRepository extends JpaRepository<WholesalerStock, Integer>, JpaSpecificationExecutor<WholesalerStock> {

}