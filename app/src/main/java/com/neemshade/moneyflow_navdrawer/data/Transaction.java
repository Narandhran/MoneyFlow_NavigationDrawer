/**
 * @author Balaji
 *
 *         13-Dec-2016 - Balaji creation Transaction.java
 */
package com.neemshade.moneyflow_navdrawer.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Balaji
 *
 */
public class Transaction implements Comparable<Transaction>{

	private Party party;

	private boolean isPurchase = true;  // seller or buyer
	private Date transDate;
	private Date dueDate;
	private String productInfo;
	private float quantity;
	private float price;
	private float leftOver;
	private String description;
	
	public Transaction()
	{
		
	}

	public void display()
	{
		System.out.println("            " + (isPurchase ? "purchase" : "sale") + ", " + transDate + " - " + dueDate + ", " + quantity + ", " + price + ", " + leftOver) ;
	}
	
	
	public static List<Transaction> fetchTransactions(Party party, boolean isPurchase)
	{
		List<Transaction> transactions = new ArrayList<Transaction>();
		
		int numOfTrans = (int) (Math.random() * 5 + 2);
		
		for(int i = 0; i < numOfTrans; i++)
		{
			transactions.add(generateTransaction(party, isPurchase));
		}
		
		
		return transactions;
	}

	private static Transaction generateTransaction(Party party, boolean isPurchase) {
		int stepDays = (int) (Math.random() * 5 + 30);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, - stepDays);
		Date transDate = cal.getTime();
		
		int dueDays = (int) (Math.random() * 7);
		if((int) (Math.random() * 100) % 2 == 0) dueDays *= -1;
//		cal.add(Calendar.DATE, dueDays);
		Calendar cal2 = Calendar.getInstance();
		cal2.add(Calendar.DATE, dueDays);
		Date dueDate = cal2.getTime();
		
		float quantity = (float) (Math.random() * 25 + 10);
		float price = (float) (Math.random() * 2500 + 3000);
		
		Transaction transaction = new Transaction();

		transaction.setParty(party);
		transaction.setPurchase(isPurchase);
		transaction.setTransDate(transDate);
		transaction.setDueDate(dueDate);
		transaction.setProductInfo("quality product");
		transaction.setQuantity(quantity);
		transaction.setPrice(price);
		transaction.setLeftOver(price);
		transaction.setDescription("get money on time");
		
		return transaction;
	}


	public int compareTo(Transaction o) {
		return getDueDate().compareTo(o.getDueDate());
	}


	public Party getParty() {
		return party;
	}

	public void setParty(Party party) {
		this.party = party;
	}

	public boolean isPurchase() {
		return isPurchase;
	}



	public void setPurchase(boolean isPurchase) {
		this.isPurchase = isPurchase;
	}



	public Date getTransDate() {
		return transDate;
	}

	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getProductInfo() {
		return productInfo;
	}

	public void setProductInfo(String productInfo) {
		this.productInfo = productInfo;
	}

	public float getQuantity() {
		return quantity;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getLeftOver() {
		return leftOver;
	}

	public void setLeftOver(float leftOver) {
		this.leftOver = leftOver;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
