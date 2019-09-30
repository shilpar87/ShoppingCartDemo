package com.shoppingcart.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.shoopingcart.model.Items;

public class CartItemsService {
	
	Date startDate = new Date();
	Date endDate = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	Items items = new Items();
	Map<String, Double> map = new HashMap<String, Double>();
	List<String> prodList = new ArrayList<String>();
	List<Double> priceList = new ArrayList<Double>();
	int milkCount = 1;
	int breadCount = 1;
	int soupCount = 1;
	int applesCount = 1;
	
	Date date = new Date();

	/** This method will get the input from user and 
	 * compares the product from property file, if it's valid or not. 
	 * If product is more than 1 quantity, then the price is multiplied by the quantity.
	 **/
	public void CartItemsMethod(String[] productNames, Map<String, Double> itemsList) {
				
		for (String prodName : productNames) {
			if (itemsList.containsKey(prodName) && prodName.equalsIgnoreCase("apples")) {
				Double price = itemsList.get(prodName);
				isWithinRange(date);
				if(isWithinRange(date)) {
					items.setPrice(price * applesCount * 10 / 100);
					items.setProductName("Apples 10% off: -10p");
				}else {
					items.setPrice(price * applesCount);
					items.setProductName("Apples");
				}
				applesCount++;
			} 
			
			if (prodName.equalsIgnoreCase("bread") && itemsList.containsKey(prodName)) {
				if (soupCount > 2 && breadCount == 1)
					items.setPrice(0.40 * breadCount);
				else {
					Double price = itemsList.get(prodName);
					items.setPrice(price  * breadCount);
				}
//				System.out.println("Bread "+getPrice());
				items.setProductName("Bread");
				breadCount++;
			}
			if (prodName.equalsIgnoreCase("milk") && itemsList.containsKey(prodName)) {
				Double price = itemsList.get(prodName);
				items.setPrice(price  * milkCount);
				items.setProductName("Milk");
				milkCount++;
			}
			if (prodName.equalsIgnoreCase("soup") && itemsList.containsKey(prodName)) {
				Double price = itemsList.get(prodName);
				items.setPrice(price  * soupCount);
//				System.out.println("Soup "+getPrice());
				items.setProductName("Soup");
				soupCount++;
			}
			map.put(items.getProductName(), items.getPrice());
		}
		
		map.forEach((k, v) -> priceList.add(v));
		map.forEach((k, v) -> prodList.add(k));
		Sum(priceList, prodList);
	}

/* 
 * Checks if the date range is within the special offer period for a week
 */
	boolean isWithinRange(Date testDate) {
		boolean flag = false;
		try {
			//Method to get startdate and end date from the property file for special offer
			getSpecialOfferDates();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		flag = testDate.after(startDate) && testDate.before(endDate);
		return flag;
		}

	/* 
	 * Calculates the sum of all selected products
	 */
	private double Sum(List<Double> priceList, List<String> prodList) {
		Double totalPrice = 0.00;
		for (Double price : priceList) {
			totalPrice = totalPrice + price;
		}
		System.out.println("\nItems are : ");
//		prodList.stream().map(name -> name.toUpperCase()).forEach(name -> System.out.print(name + "\n"));
		map.forEach((k,v)->System.out.println(k + " - £ " + v));
		System.out.println("================");
		System.out.println("\nSubtotal £" + totalPrice);
		return totalPrice;

	}
	
	public Map<String,Double> getPropValues() throws IOException {
		InputStream inputStream = null;
		Map<String,Double> itemPrice = new HashMap<String,Double>();
		try {
			Properties prop = new Properties();
			String itemProeprty = "resources/itemlist.properties";
			inputStream = getClass().getClassLoader().getResourceAsStream(itemProeprty);

			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + itemProeprty + "' not found in the classpath");
			}

			for (String key : prop.stringPropertyNames()) {
				String value = prop.getProperty(key);
				itemPrice.put(key,  Double.valueOf(value));
			}
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}
		return itemPrice;
	}

	public void getSpecialOfferDates() throws IOException {
		InputStream inputStream = null;
		String date1="";
		String date2="";
		try {
			Properties prop = new Properties();
			String itemProeprty = "resources/specialoffers.properties";
			inputStream = getClass().getClassLoader().getResourceAsStream(itemProeprty);

			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + itemProeprty + "' not found in the classpath");
			}

			date1=prop.getProperty("startDate");
			date2=prop.getProperty("endDate");
			startDate=sdf.parse(date1);
			endDate=sdf.parse(date2);			
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}
	}
}
