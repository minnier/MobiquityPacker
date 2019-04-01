package com.mobiquityinc.packer.model;

import java.util.List;

public interface APackage {

	public Double getMaxWeight();

	public void setMaxWeight(Double maxWeight);

	public List<APackageItem> getItems();

	public void addItem(APackageItem item);

}
