package com.barrouh.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "wholesaler_stock")
public class WholesalerStock implements Serializable {

	private static final long serialVersionUID = 1L;

	private WholesalerStockId id;
	private Beer beer;
	private int quantity;
	private Wholesaler wholesaler;

	@EmbeddedId
	public WholesalerStockId getId() {
		return id;
	}

	public void setId(WholesalerStockId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(insertable = false, updatable = false)
	public Beer getBeer() {
		return beer;
	}

	public void setBeer(Beer beer) {
		this.beer = beer;
	}

	@Column(name = "quantity", nullable = false)
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(insertable = false, updatable = false)
	public Wholesaler getWholesaler() {
		return wholesaler;
	}

	public void setWholesaler(Wholesaler wholesaler) {
		this.wholesaler = wholesaler;
	}

}