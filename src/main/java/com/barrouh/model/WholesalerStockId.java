package com.barrouh.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class WholesalerStockId implements Serializable {

	private static final long serialVersionUID = 1L;
	private int wholesalerId;
	private int beerId;

	@Column(name = "wholesaler_id", nullable = false)
	public int getWholesalerId() {
		return wholesalerId;
	}

	public void setWholesalerId(int wholesalerId) {
		this.wholesalerId = wholesalerId;
	}

	@Column(name = "beer_id", nullable = false)
	public int getBeerId() {
		return beerId;
	}

	public void setBeerId(int beerId) {
		this.beerId = beerId;
	}

}