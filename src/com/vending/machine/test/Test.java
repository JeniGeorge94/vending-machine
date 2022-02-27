package com.vending.machine.test;

import java.util.Arrays;
import java.util.List;

import com.vending.machine.ProductAndChange;
import com.vending.machine.VendingMachine;
import com.vending.machine.coin.Coin;
import com.vending.machine.exception.VendingMachineException;
import com.vending.machine.supplier.SupplierVendingMachineImpl;
import com.vending.machine.user.UserVendingMachineImpl;

public class Test {

	public static void main(String[] args) {

		VendingMachine supplier = new SupplierVendingMachineImpl();

		List<String> productNames = Arrays.asList("PEPSI", "COKE", "LAYS", "CHIPS");
		List<Integer> prices = Arrays.asList(20, 25, 2, 1);

		for (int j = 0; j < 30; j++) {
			for (int i = 0; i < productNames.size(); i++) {
				try {
					supplier.addProduct(productNames.get(i), prices.get(i));
				} catch (VendingMachineException e) {
					e.printStackTrace();
				}
			}
		}
		
//		try {
//			supplier.addProduct("TEST", 50);
//		} catch (VendingMachineException e1) {
//			e1.printStackTrace();
//		}
		
//		for(int i=0;i<125;i++) {
//			try {
//				supplier.insertCoin(Arrays.asList(Coin.ONE, Coin.TWO, Coin.FIVE, Coin.TEN));
//			} catch (VendingMachineException e1) {
//				e1.printStackTrace();
//			}
//		}

//		try {
//			supplier.takeCash();
//		} catch (VendingMachineException e1) {
//			e1.printStackTrace();
//		}
		
//		try {
//			supplier.takeCash();
//		} catch (VendingMachineException e1) {
//			e1.printStackTrace();
//		}
		
		VendingMachine user = new UserVendingMachineImpl();
		try {
			user.insertCoin(Arrays.asList(Coin.TEN, Coin.FIVE, Coin.TWO, Coin.TWO, Coin.TWO, Coin.ONE));
			ProductAndChange productAndChange = user.getProductAndChange("PEPSI");
			System.out.println("Product " + productAndChange);
		} catch (VendingMachineException e) {
			e.printStackTrace();
		}
		
//		try {
//			user.insertCoin(Arrays.asList(Coin.TEN, Coin.TEN));
//			ProductAndChange productAndChange = user.getProductAndChange("COKE");
//			System.out.println("Product " + productAndChange);
//		} catch (VendingMachineException e) {
//			e.printStackTrace();
//		}
		
//		try {
//			List<Coin> refund = user.cancel();
//			System.out.println("Refund = " + refund);
//		} catch (VendingMachineException e) {
//			e.printStackTrace();
//		}

	}

}
