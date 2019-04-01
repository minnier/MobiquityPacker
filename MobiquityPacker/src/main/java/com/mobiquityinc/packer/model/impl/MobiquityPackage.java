package com.mobiquityinc.packer.model.impl;

import java.util.ArrayList;
import java.util.List;

import com.mobiquityinc.packer.model.APackage;
import com.mobiquityinc.packer.model.APackageItem;

public class MobiquityPackage implements APackage {

	private Double maxWeight;
	private List<APackageItem> items = new ArrayList<APackageItem>();

	public Double getMaxWeight() {
		return maxWeight;
	}

	public void setMaxWeight(Double maxWeight) {
		this.maxWeight = maxWeight;
	}

	public List<APackageItem> getItems() {
		return items;
	}

	public void addItem(APackageItem item) {
		this.items.add(item);
	}

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();

		items.forEach(item -> {
			result.append(item.getIndexNumber());
			result.append(",");

		});

		if (!items.isEmpty())
			result.deleteCharAt(result.length() - 1);

		return result.toString();
	}

}
