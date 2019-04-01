package com.mobiquityinc.packer;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import com.mobiquityinc.exceptions.APIException;
import com.mobiquityinc.packer.model.APackage;
import com.mobiquityinc.packer.model.APackageItem;
import com.mobiquityinc.packer.model.impl.MobiquityPackage;
import com.mobiquityinc.packer.model.impl.MobiquityPackageItem;

public class PackerTests {

	public static final String ONE_DATA_DATASET = "src/test/resources/testPackages1.txt";
	public static final String TWO_DATA_DATASET = "src/test/resources/testPackages2.txt";
	public static final String ALL_DATA_DATASET = "src/test/resources/testPackages3.txt";
	public static final String INVALID_DATA_DATASET = "src/test/resources/testPackages4.txt";
	public static final String WRONG_FILE_PATH = "doesnotexist/packages.txt";

	@Test
	public void whenReadDataFileCount1() throws IOException {
		Path path = Paths.get(ONE_DATA_DATASET);

		assertEquals(1, Files.readAllLines(path).size());
	}
	
	@Test
	public void whenReadDataFileCountAll() throws IOException {
		Path path = Paths.get(ALL_DATA_DATASET);

		assertEquals(4, Files.readAllLines(path).size());
	}
	
	@Test
	public void whenReadDataFileLine1_thenCorrect() throws IOException {
		String expectedValue = "81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)";

		Path path = Paths.get(ONE_DATA_DATASET);

		String read = Files.readAllLines(path).get(0);
		assertEquals(expectedValue, read);
	}
	
	@Test
	public void whenReadDataFileLine2_thenCorrect() throws IOException {
		String expectedValue = "8 : (1,15.3,€34)";

		Path path = Paths.get(TWO_DATA_DATASET);

		String read = Files.readAllLines(path).get(1);
		assertEquals(expectedValue, read);
	}

	@Test
	public void whenReadDataFileAndPack_thenCorrect() throws APIException {
		String expectedValue = "4\n-\n2,7\n8,9\n";

		String read = Packer.pack(ALL_DATA_DATASET);
		assertEquals(expectedValue, read);
	}

	@Test(expected = APIException.class)
	public void whenReadDataFileNotExist_thenException() throws APIException {
		String read = Packer.pack(WRONG_FILE_PATH);

		assertEquals(null, read);
	}
	
	@Test(expected = APIException.class)
	public void whenReadDataInvalid_thenException() throws APIException {
		String read = Packer.pack(INVALID_DATA_DATASET);

		assertEquals(null, read);
	}
	
	@Test
	public void whenNewPackageMaxWeightSet() {
		
		Double randomWeight = Math.random() * 100;
		
		APackage mobiquityPackage = new MobiquityPackage();
		mobiquityPackage.setMaxWeight(randomWeight);
		
		assertEquals(randomWeight, mobiquityPackage.getMaxWeight());
	}
	
	@Test
	public void whenNewPackageItemSet() {

		APackageItem mobiquityPackageItem = new MobiquityPackageItem();
		mobiquityPackageItem.setIndexNumber((int)Math.random()*10);
		mobiquityPackageItem.setCost(new BigDecimal(Math.random()*100));
		mobiquityPackageItem.setWeight(new Double(Math.random()*100));
		
		MobiquityPackage mobiquityPackage = new MobiquityPackage();
		mobiquityPackage.addItem(mobiquityPackageItem);
		
		assertEquals(mobiquityPackageItem, mobiquityPackage.getItems().get(0));
		assertEquals(mobiquityPackageItem.getIndexNumber(), mobiquityPackage.getItems().get(0).getIndexNumber());
		assertEquals(mobiquityPackageItem.getCost(), mobiquityPackage.getItems().get(0).getCost());
		assertEquals(mobiquityPackageItem.getWeight(), mobiquityPackage.getItems().get(0).getWeight());
	}
}
