package com.barrouh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.barrouh.model.Beer;

@Repository
public interface BeerRepository extends JpaRepository<Beer, Integer>, JpaSpecificationExecutor<Beer> {

}