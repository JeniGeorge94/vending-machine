package com.vending.machine;

import java.util.List;
import java.util.Map;

import com.vending.machine.coin.Coin;
import com.vending.machine.exception.VendingMachineException;

public interface VendingMachine {

	default public void addProduct(String productName, int price) throws VendingMachineException {
		throw new VendingMachineException("Adding Product is not allowed");
	}

	public void insertCoin(List<Coin> coins) throws VendingMachineException;

	default public List<Coin> takeCash() throws VendingMachineException {
		throw new VendingMachineException("Taking out cash is not allowed");
	}

	public Map<String, Integer> getProductsAndPrice() throws VendingMachineException;

	default public ProductAndChange getProductAndChange(String productName) throws VendingMachineException {
		return new ProductAndChange();
	}
	
	default public List<Coin> cancel() throws VendingMachineException {
		throw new VendingMachineException("Cancel is not allowed");
	}
	
}
