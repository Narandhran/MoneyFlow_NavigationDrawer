/**
 * @author Balaji
 *
 *         13-Dec-2016 - Balaji creation Party.java
 */
package com.neemshade.moneyflow_navdrawer.data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Balaji
 *
 */
public class Party {

	private Name name;
	private int defaultDueDays = 45;
	private List<Transaction> transactions;
	private List<Payment> payments;
	
	public Party(Name name, int defaultDueDays)
	{
		this.name = name;
		this.setDefaultDueDays(defaultDueDays);
	}
	
	public void addTransaction(Transaction transaction)
	{
		if(transactions == null)
			transactions = new ArrayList<Transaction>();
		
		transactions.add(transaction);
	}
	
	public void addPayment(Payment payment)
	{
		if(payments == null)
			payments = new ArrayList<Payment>();
		
		payments.add(payment);
	}
	
	public void display()
	{
		System.out.println("      " + getName().displayName() + ", " + getName().getLocation());
		for (Transaction transaction : transactions) {
			transaction.display();
		}
	}
	
	
	public static List<Party> fetchParties()
	{
		List<Party> parties = new ArrayList<Party>();
		
		for(int i = 0; i < 3; i++)
		{
			parties.add(generateParty(true));
			parties.add(generateParty(false));
		}
		
		
		return parties;
	}

	private static Party generateParty(boolean isPurchase) {
		int days = (int) (Math.random() * 15 + 30);
		Party party = new Party(Name.getNextName(), days);
		party.setTransactions(Transaction.fetchTransactions(party, isPurchase));
		return party;
	}


	public static Party findParty(List<Party> parties, String partyName)
	{
		if(parties == null || partyName == null)
			return null;

		for (Party party : parties) {
			if(partyName.equals(party.getName().getName()) ||
					partyName.equals(party.getName().displayName()))
				return party;
		}

		return null;
	}
	

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public int getDefaultDueDays() {
		return defaultDueDays;
	}

	public void setDefaultDueDays(int defaultDueDays) {
		this.defaultDueDays = defaultDueDays;
	}


	public List<Transaction> getTransactions() {
		return transactions;
	}


	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public List<Payment> getPayments() {
		return payments;
	}

	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}
}
