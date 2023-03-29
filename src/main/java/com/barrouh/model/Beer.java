package com.barrouh.model;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
	private BigDecimal price;
	private Integer breweryId;
	private Brewery brewery;

	@Id
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
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Column(name = "brewery_id", nullable = false)
	public Integer getBreweryId() {
		return breweryId;
	}

	public void setBreweryId(Integer breweryId) {
		this.breweryId = breweryId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "brewery_id", insertable = false, updatable = false)
	public Brewery getBrewery() {
		return brewery;
	}

	public void setBrewery(Brewery brewery) {
		this.brewery = brewery;
	}

}