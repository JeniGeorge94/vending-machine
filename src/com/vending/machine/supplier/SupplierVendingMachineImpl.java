package com.vending.machine.supplier;

import java.util.List;
import java.util.Map;

import com.vending.machine.VendingMachine;
import com.vending.machine.cache.VMCache;
import com.vending.machine.coin.Coin;
import com.vending.machine.exception.VendingMachineException;

public class SupplierVendingMachineImpl implements VendingMachine {

	@Override
	public void addProduct(String productName, int price) throws VendingMachineException {
		VMCache.getInstance().addProduct(productName, price);
	}

	@Override
	public void insertCoin(List<Coin> coins) throws VendingMachineException {
		VMCache.getInstance().addCash(coins);
	}

	@Override
	public List<Coin> takeCash() throws VendingMachineException {
		return VMCache.getInstance().takeCash();
	}

	@Override
	public Map<String, Integer> getProductsAndPrice() throws VendingMachineException {
		return VMCache.getInstance().getProductsAndPrice();
	}
	
}
