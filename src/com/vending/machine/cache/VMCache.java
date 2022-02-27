package com.vending.machine.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vending.machine.ProductAndChange;
import com.vending.machine.coin.Coin;
import com.vending.machine.exception.VendingMachineException;

public class VMCache {

	private static VMCache instance = null;

	private Map<String, Integer> productCountMap = new HashMap<>();
	private Map<String, Integer> productPriceMap = new HashMap<>();
	private List<String> products = new ArrayList<>();
	private List<Coin> coins = new ArrayList<>();
	private Map<Coin, Integer> coinMap = new HashMap<>();
	private int totalBalance = 0;

	private VMCache() {
	}

	public static VMCache getInstance() {
		synchronized (VMCache.class) {
			if (instance == null) {
				instance = new VMCache();
			}
		}
		return instance;
	}

	public void addProduct(String productName, int price) throws VendingMachineException {
		if (products.size() >= 120) {
			throw new VendingMachineException("Maximum products size exceeded");
		}
		products.add(productName);
		productPriceMap.put(productName, price);
		Integer productCount = productCountMap.get(productName);
		if (productCount == null) {
			productCountMap.put(productName, 1);
		} else {
			productCountMap.put(productName, productCount + 1);
		}
	}

	public void addCash(List<Coin> coinsToInsert) throws VendingMachineException {
		if (this.coins.size() >= 500) {
			throw new VendingMachineException("No space for coins");
		}
		if (this.coins.size() + coinsToInsert.size() > 500) {
			int coinsToAdd = 500 - this.coins.size();
			throw new VendingMachineException("Can add only " + coinsToAdd + " coins more");
		}

		this.coins.addAll(coinsToInsert);
		for (Coin coin : coinsToInsert) {
			totalBalance = totalBalance + coin.getValue();
			if (coinMap.get(coin) == null) {
				coinMap.put(coin, 1);
			} else {
				coinMap.put(coin, coinMap.get(coin) + 1);
			}
		}
	}

	public List<Coin> takeCash() throws VendingMachineException {
		if (this.coins.size() == 0) {
			throw new VendingMachineException("Cash is empty");
		}

		List<Coin> cash = new ArrayList<>(this.coins);
		this.coins.clear();
		coinMap.clear();
		return cash;
	}

	public Map<String, Integer> getProductsAndPrice() throws VendingMachineException {
		if (productPriceMap.isEmpty()) {
			throw new VendingMachineException("Vending Machine is empty");
		}
		return productPriceMap;
	}

	public ProductAndChange getProductAndChange(String productName, List<Coin> insertedCoins)
			throws VendingMachineException {
		Integer productCount = productCountMap.get(productName);
		if (productCount == null || productCount <= 0) {
			throw new VendingMachineException("Product does not exist");
		}
		Integer productPrice = productPriceMap.get(productName);
		int insertedAmount = 0;
		for (Coin coin : insertedCoins) {
			insertedAmount = insertedAmount + coin.getValue();
		}
		int balanceToReturn = 0;
		if (insertedAmount < productPrice) {
			int bal = productPrice - insertedAmount;
			throw new VendingMachineException("Not fully paid. Pay " + bal + " more.");
		} else if (insertedAmount > productPrice) {
			balanceToReturn = insertedAmount - productPrice;
		} else {
			balanceToReturn = 0;
		}

		List<Coin> change = getChange(balanceToReturn);

		productCountMap.put(productName, productCount - 1);
		products.remove(productName);
		ProductAndChange productAndChange = new ProductAndChange(productName, change);
		return productAndChange;
	}

	public List<Coin> cancel(List<Coin> insertedCoins) {
		for (Coin coin : insertedCoins) {
			this.coins.remove(coin);
			coinMap.put(coin, coinMap.get(coin) - 1);
		}
		return new ArrayList<>(insertedCoins);
	}

	private List<Coin> getChange(long amount) throws VendingMachineException {
		List<Coin> changes = new ArrayList<Coin>();
		Map<Coin, Integer> coinCount = new HashMap<>();
		if (amount > 0) {
			long balance = amount;
			while (balance > 0) {
				if (balance >= Coin.TEN.getValue() && coinMap.get(Coin.TEN) != null
						&& coinMap.get(Coin.TEN) - getCountOfCoinInMap(coinCount, Coin.TEN) > 0) {
					changes.add(Coin.TEN);
					balance = balance - Coin.TEN.getValue();
					coinCount = addCoinCount(coinCount, Coin.TEN);
					continue;
				} else if (balance >= Coin.FIVE.getValue() && coinMap.get(Coin.FIVE) != null
						&& coinMap.get(Coin.FIVE) - getCountOfCoinInMap(coinCount, Coin.FIVE) > 0) {
					changes.add(Coin.FIVE);
					balance = balance - Coin.FIVE.getValue();
					coinCount = addCoinCount(coinCount, Coin.FIVE);
					continue;
				} else if (balance >= Coin.TWO.getValue() && coinMap.get(Coin.TWO) != null
						&& coinMap.get(Coin.TWO) - getCountOfCoinInMap(coinCount, Coin.TWO) > 0) {
					changes.add(Coin.TWO);
					balance = balance - Coin.TWO.getValue();
					coinCount = addCoinCount(coinCount, Coin.TWO);
					continue;
				} else if (balance >= Coin.ONE.getValue() && coinMap.get(Coin.ONE) != null
						&& coinMap.get(Coin.ONE) - getCountOfCoinInMap(coinCount, Coin.ONE) > 0) {
					changes.add(Coin.ONE);
					balance = balance - Coin.ONE.getValue();
					coinCount = addCoinCount(coinCount, Coin.ONE);
					continue;
				} else {
					throw new VendingMachineException("No Sufficient Change, Please try another product or cancel");
				}
			}
		}

		return changes;
	}

	public Map<Coin, Integer> addCoinCount(Map<Coin, Integer> coinCount, Coin coin) {
		if (coinCount.get(coin) == null) {
			coinCount.put(coin, 1);
		} else {
			coinCount.put(coin, coinCount.get(coin) + 1);
		}
		return coinCount;
	}

	public int getCountOfCoinInMap(Map<Coin, Integer> coinCount, Coin coin) {
		int count = coinCount.get(coin) == null ? 0 : coinCount.get(coin);
		return count;
	}

}
