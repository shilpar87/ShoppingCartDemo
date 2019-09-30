package com.shopping.main;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.shoppingcart.service.CartItemsService;

public class ShoppingCartMain {

	public static void main(String[] args) {
	
		CartItemsService cartService = new CartItemsService();
		Map<String, Double> itemPrice = new HashMap<String, Double>();
		try {
			itemPrice = cartService.getPropValues();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Set<String> validInput = itemPrice.keySet();

		Scanner in = new Scanner(System.in);
		System.out.println("Enter the number of Items \n");
		int length = 0;
		
	/* 
	 * Compares the product entered by user is valid or not from the property file itemlist.properties
	 */
		if (in.hasNextInt()) {
			length = in.nextInt();			
			System.out.println("Enter items \n");
			String[] input = new String[length];
			for (int i = 0; i < length; i++) {
				input[i] = in.next();
				if (!validInput.contains(input[i])) {
					System.out.println("Invalid Product! Available products are: ");
					validInput.forEach(System.out::println);
					in.reset();
					i--;
				}
			}
			in.close();
			cartService.CartItemsMethod(input,itemPrice);
		} else {
			System.out.println("Invalid Input!!!Only numbers allowed, please try again ");
		}
	}

}
