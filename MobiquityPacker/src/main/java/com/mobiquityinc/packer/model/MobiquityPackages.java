package com.mobiquityinc.packer.model;

import java.util.ArrayList;
import java.util.List;

import com.mobiquityinc.packer.model.impl.MobiquityPackage;

/*
 * Model to store list of packages that could be packed with items.
 */
public class MobiquityPackages {

	private long numberOfPackedPackages = 0;
	private List<APackage> packageList = new ArrayList<APackage>();

	/*
	 * Packages that are empty are ignored
	 */
	public long getNumberOfPackedPackages() {
		return this.getPackageList().stream().filter(x -> x.getItems().size() > 0).count();
	}

	public List<APackage> getPackageList() {
		return packageList;
	}

	public void addPackage(MobiquityPackage mobiquityPackage) {
		this.packageList.add(mobiquityPackage);
	}

	@Override
	public String toString() {

		StringBuffer result = new StringBuffer();

		this.getPackageList().forEach(mobiquityPackage -> {
			if (!mobiquityPackage.getItems().isEmpty()) {
				result.append(mobiquityPackage.toString());
				result.append("\n"); // System.lineSeparator()
			} else {
				result.append("-");
				result.append("\n");
			}
		});

		return result.toString();
	}

}
