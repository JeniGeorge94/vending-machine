package com.vending.machine.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.vending.machine.ProductAndChange;
import com.vending.machine.VendingMachine;
import com.vending.machine.cache.VMCache;
import com.vending.machine.coin.Coin;
import com.vending.machine.exception.VendingMachineException;

public class UserVendingMachineImpl implements VendingMachine {

	private List<Coin> insertedCoins = new ArrayList<>();

	@Override
	public void insertCoin(List<Coin> coins) throws VendingMachineException {
		VMCache.getInstance().addCash(coins);
		insertedCoins.addAll(coins);
	}

	@Override
	public Map<String, Integer> getProductsAndPrice() throws VendingMachineException {
		return VMCache.getInstance().getProductsAndPrice();
	}

	@Override
	public ProductAndChange getProductAndChange(String productName) throws VendingMachineException {
		ProductAndChange productAndChange = VMCache.getInstance().getProductAndChange(productName, insertedCoins);
		insertedCoins.clear();
		return productAndChange;
	}

	@Override
	public List<Coin> cancel() throws VendingMachineException {
		List<Coin> refund = VMCache.getInstance().cancel(insertedCoins);
		System.out.println("UserVendingMachineImpl.cancel() insertedCoins = " + insertedCoins);
		insertedCoins.clear();
		return refund;
	}

}
