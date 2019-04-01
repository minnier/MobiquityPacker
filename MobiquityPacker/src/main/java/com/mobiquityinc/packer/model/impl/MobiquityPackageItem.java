package com.mobiquityinc.packer.model.impl;

import java.math.BigDecimal;

import com.mobiquityinc.packer.model.APackageItem;

public class MobiquityPackageItem implements APackageItem {

	private int indexNumber;
	private Double weight;
	private BigDecimal cost;

	public int getIndexNumber() {
		return indexNumber;
	}

	public void setIndexNumber(int indexNumber) {
		this.indexNumber = indexNumber;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public BigDecimal getCost() {
		return cost;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + indexNumber;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MobiquityPackageItem other = (MobiquityPackageItem) obj;
		if (indexNumber != other.indexNumber)
			return false;
		return true;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	@Override
	public String toString() {
		return "Item [indexNumber=" + indexNumber + ", weight=" + weight + ", cost=" + cost + "]";
	}

}
