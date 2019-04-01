package com.mobiquityinc.packer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mobiquityinc.exceptions.APIException;
import com.mobiquityinc.exceptions.ItemMaxCostExceededException;
import com.mobiquityinc.exceptions.ItemMaxWeightExceededException;
import com.mobiquityinc.exceptions.PackageMaxWeightExceededException;
import com.mobiquityinc.packer.model.MobiquityPackage;
import com.mobiquityinc.packer.model.MobiquityPackageItem;
import com.mobiquityinc.packer.model.MobiquityPackages;

public class Packer {

	private static final String EURO_SYMBOL = "€";
	private static final double PACKAGE_MAX_WEIGHT = 100;
	private static final double ITEM_MAX_WEIGHT = 100;
	private static final double ITEM_MAX_COST = 100;

	public static void main(String[] args) throws APIException {
		System.out.println(pack("src/main/resources/packages.txt"));
	}

	private static Logger logger = LoggerFactory.getLogger(Packer.class);

	/**
	 * @param filePath
	 * @return solution as a String
	 * @throws APIException if incorrect parameters are being passed
	 */
	public static String pack(String filePath) throws APIException {

		logger.debug("Pack items into package");

		try {
			MobiquityPackages packages = getAllPackages(filePath);

			optimizePacks(packages.getPackageList());

			return packages.toString();
		} catch (APIException aex) {
			logger.error(aex.getMessage());
			throw aex;
		}

	}

	/*
	 * optimize all packages
	 */
	private static void optimizePacks(List<MobiquityPackage> packages) {

		logger.debug("Optimizing packages");

		for (MobiquityPackage mobiquiquityPackage : packages) {
			PackOptimizer.optimize(mobiquiquityPackage);
		}

	}

	/*
	 * Read the input file. Parse the data line for line. Validate the additional
	 * constraints
	 */
	private static MobiquityPackages getAllPackages(String filePath) throws APIException {

		logger.debug("Read data file");

		MobiquityPackages result = new MobiquityPackages();

		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF8"))) {

			logger.debug("Parsing lines");

			String line;
			while ((line = br.readLine()) != null) {
				MobiquityPackage obj = new MobiquityPackage();

				// parse maxWeight
				int idx = line.indexOf(":");
				if (idx > 1) {
					String temp = line.substring(0, idx);
					obj.setMaxWeight(new Double(temp.trim()));

					// validate package max weight
					validatePackageConstraints(obj);
				}

				// parse items
				int offset = idx;
				idx = line.indexOf("(", offset);
				while (idx >= 0) {
					int end = line.indexOf(")", idx);
					String temp = line.substring(idx + 1, end);
					String[] arr = temp.split(",");

					MobiquityPackageItem item = new MobiquityPackageItem();
					item.setIndexNumber(new Integer(arr[0]));
					item.setWeight(new Double(arr[1]));
					item.setCost(new BigDecimal(arr[2].replaceAll(EURO_SYMBOL, "")));
					obj.addItem(item);

					validateItemConstraints(item);

					offset = idx + 1;
					idx = line.indexOf("(", offset);
				}

				// validate constraints
				validatePackageConstraints(obj);

				// add package
				result.addPackage(obj);
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new APIException("Incorrect parameters are being passed");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new APIException("Incorrect parameters are being passed");
		}

		return result;
	}

	/*
	 * Validate constraints from the input file data relating the the package
	 * maximum weight.
	 */
	private static void validatePackageConstraints(MobiquityPackage mobiquityPackage)
			throws PackageMaxWeightExceededException {

		logger.debug("Validating package constraints");

		if (mobiquityPackage.getMaxWeight().doubleValue() > PACKAGE_MAX_WEIGHT) {
			throw new PackageMaxWeightExceededException("Maximum weight exceeded for package");
		}
	}

	/*
	 * Validate constraints from the input file data relating the the items'
	 * maximum weight and maximum item cost.
	 */
	private static void validateItemConstraints(MobiquityPackageItem mobiquityPackageItem)
			throws ItemMaxWeightExceededException, ItemMaxCostExceededException {

		logger.debug("Validating item constraints");

		if (mobiquityPackageItem.getWeight().doubleValue() > ITEM_MAX_WEIGHT) {
			throw new ItemMaxWeightExceededException("Maximum weight exceeded for item");
		}

		if (mobiquityPackageItem.getCost().doubleValue() > ITEM_MAX_COST) {
			throw new ItemMaxWeightExceededException("Maximum cost exceeded for item");
		}
	}

}
