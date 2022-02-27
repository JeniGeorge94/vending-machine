package com.vending.machine;

import java.util.List;

import com.vending.machine.coin.Coin;

public class ProductAndChange {
	private String productName;
	private List<Coin> balance;

	public ProductAndChange() {
		super();
	}

	public ProductAndChange(String productName, List<Coin> balance) {
		super();
		this.productName = productName;
		this.balance = balance;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public List<Coin> getBalance() {
		return balance;
	}

	public void setBalance(List<Coin> balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "ProductAndChange [productName=" + productName + ", balance=" + balance + "]";
	}

}
