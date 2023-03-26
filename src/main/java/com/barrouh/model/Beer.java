package com.barrouh.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "beer")
public class Beer implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	private float alcoholPercentage;
	private float price;
	private Brewery brewery;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name", unique = true, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "alcohol_percentage", nullable = false)
	public float getAlcoholPercentage() {
		return alcoholPercentage;
	}

	public void setAlcoholPercentage(float alcoholPercentage) {
		this.alcoholPercentage = alcoholPercentage;
	}


	@Column(name = "price", nullable = false)
	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	public Brewery getBrewery() {
		return brewery;
	}

	public void setBrewery(Brewery brewery) {
		this.brewery = brewery;
	}

}