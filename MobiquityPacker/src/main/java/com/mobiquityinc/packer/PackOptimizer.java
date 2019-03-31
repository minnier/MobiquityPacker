package com.mobiquityinc.packer;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mobiquityinc.packer.model.MobiquityPackage;
import com.mobiquityinc.packer.model.MobiquityPackageItem;

public class PackOptimizer {

	private static Logger logger = LoggerFactory.getLogger(PackOptimizer.class);
	
	/*
	 * optimize a single package
	 */
	protected static void optimize(MobiquityPackage mobiquityPackage) {

		logger.debug("Optimizing a single package");

		// use to store different item packing scenarios
		Map<Integer, Set<Integer>> scenarios = new HashMap<Integer, Set<Integer>>();
		
		BigDecimal maxPackedCost = new BigDecimal(0.0);
		Double maxPackedWeight = new Double(0.0);
		Integer maxPackedCostIndex = 0;

		// to be used to loop items from various start positions
		Queue<Integer> queue = new LinkedList<>();

		// add arraylist positions to queue
		for (int i = 0; i < mobiquityPackage.getItems().size(); i++) {
			queue.add(i);
		}

		// queue will be used to start packing from different item start positions
		while (!queue.isEmpty()) {
			// HashSet (faster than TreeSet), used to store items that exceeds package weight
			Set<Integer> notPickedItems = new HashSet<Integer>();

			int currentIndexNumber = queue.remove();

			Double packedWeight = 0.0;
			BigDecimal packedCost = new BigDecimal(0.0);

			// start from current position to end
			for (int i = currentIndexNumber; i < mobiquityPackage.getItems().size(); i++) {
				MobiquityPackageItem item = mobiquityPackage.getItems().get(i);
				if ((packedWeight + item.getWeight()) < mobiquityPackage.getMaxWeight()) {
					packedWeight += item.getWeight();
					packedCost = packedCost.add(item.getCost());
				} else {
					notPickedItems.add(item.getIndexNumber());
				}
			}

			// start from beginning to before current position
			for (int i = 0; i < currentIndexNumber; i++) {
				MobiquityPackageItem item = mobiquityPackage.getItems().get(i);
				if ((packedWeight + item.getWeight()) < mobiquityPackage.getMaxWeight()) {
					packedWeight += item.getWeight();
					packedCost = packedCost.add(item.getCost());
				} else {
					notPickedItems.add(item.getIndexNumber());
				}
			}

			if (maxPackedWeight.doubleValue() == 0.0)
				maxPackedWeight = packedWeight; // set first value

			if (packedCost.compareTo(maxPackedCost) > 0) {
				maxPackedCost = packedCost;
				maxPackedCostIndex = currentIndexNumber;
			} else if (packedCost.compareTo(maxPackedCost) == 0) {
				if (packedWeight.compareTo(maxPackedWeight) < 0) {
					// prefer to send a package which weighs less in case of same price
					maxPackedCost = packedCost;
					maxPackedCostIndex = currentIndexNumber;
				}
			}

			scenarios.put(currentIndexNumber, notPickedItems);
		}

		for (Integer i : scenarios.get(maxPackedCostIndex)) {
			for (MobiquityPackageItem item : mobiquityPackage.getItems()) {
				if (item.getIndexNumber() == i) {
					mobiquityPackage.getItems().remove(item);
					break;
				}
			}
		}

	}

}
