package com.barrouh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.barrouh.model.Brewery;

@Repository
public interface BreweryRepository extends JpaRepository<Brewery, Integer>, JpaSpecificationExecutor<Brewery> {

}