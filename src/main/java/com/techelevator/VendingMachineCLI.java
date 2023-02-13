package com.techelevator;

import java.io.IOException;

public class VendingMachineCLI {

	public void run(){
		// entry point for the vending machine
		boolean play = true;

		while (play) {
			VendingMachine vending = new VendingMachine();
			vending.vendingMachine();
			play = false;
		}

	}

	public static void main(String[] args){
		VendingMachineCLI cli = new VendingMachineCLI();
		cli.run();
	}
}
