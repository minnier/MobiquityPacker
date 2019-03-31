package com.mobiquityinc.packer.model;

import java.util.ArrayList;
import java.util.List;

/*
 * Model to store a single package that could be packed with items.
 */
public class MobiquityPackage {

	private Double maxWeight;
	private List<MobiquityPackageItem> items = new ArrayList<MobiquityPackageItem>();

	public Double getMaxWeight() {
		return maxWeight;
	}

	public void setMaxWeight(Double maxWeight) {
		this.maxWeight = maxWeight;
	}

	public List<MobiquityPackageItem> getItems() {
		return items;
	}

	public void addItem(MobiquityPackageItem item) {
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
