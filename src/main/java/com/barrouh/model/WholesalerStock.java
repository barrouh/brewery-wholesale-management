package com.barrouh.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@JsonIgnoreProperties({ "hibernateLazyInitializer" })
public class WholesalerStock implements Serializable {

	private static final long serialVersionUID = 1L;

	private WholesalerStockId id;
	private int quantity;
	private Beer beer;
	private Wholesaler wholesaler;

	public WholesalerStock(WholesalerStockId id, int quantity) {
		this.id = id;
		this.quantity = quantity;
	}

	@EmbeddedId
	public WholesalerStockId getId() {
		return id;
	}

	public void setId(WholesalerStockId id) {
		this.id = id;
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
	public Beer getBeer() {
		return beer;
	}

	public void setBeer(Beer beer) {
		this.beer = beer;
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