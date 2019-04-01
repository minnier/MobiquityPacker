package com.mobiquityinc.packer.model;

import java.math.BigDecimal;

public interface APackageItem {

	public int getIndexNumber();

	public void setIndexNumber(int indexNumber);

	public Double getWeight();

	public void setWeight(Double weight);

	public BigDecimal getCost();

	public void setCost(BigDecimal cost);

}
