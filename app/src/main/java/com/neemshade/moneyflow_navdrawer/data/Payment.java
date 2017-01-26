/**
 * @author Balaji
 *
 *         13-Dec-2016 - Balaji creation PaymentFragment.java
 */
package com.neemshade.moneyflow_navdrawer.data;

import java.util.Date;

/**
 * @author Balaji
 *
 */
public class Payment implements  Comparable<Payment>{

	private Party party;

	private boolean isPaid; // paid or received
	private float amount;
	private float leftOver;
	private Date paymentDate;
	
	public Payment()
	{
		
	}
	
	public Payment(boolean isPaid, float amount, float leftOver, Date paymentDate)
	{
		this.isPaid = isPaid;
		this.amount = amount;
		this.leftOver = leftOver;
		this.paymentDate = paymentDate;
	}

	public boolean isPaid() {
		return isPaid;
	}

	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public float getLeftOver() {
		return leftOver;
	}

	public void setLeftOver(float leftOver) {
		this.leftOver = leftOver;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Party getParty() {
		return party;
	}

	public void setParty(Party party) {
		this.party = party;
	}

	@Override
	public int compareTo(Payment payment) {
		return this.getPaymentDate().compareTo(payment.getPaymentDate());
	}
}
