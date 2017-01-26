package com.neemshade.moneyflow_navdrawer.services;

import com.neemshade.moneyflow_navdrawer.data.Party;
import com.neemshade.moneyflow_navdrawer.data.Payment;
import com.neemshade.moneyflow_navdrawer.data.Transaction;

import java.util.Collections;
import java.util.List;

/**
 * Created by Balaji on 21-12-2016.
 */

public class PaymentIncluder extends EntityIncluder {

    public void addPayment(Payment payment)
    {
        Party party = payment.getParty();
        if(party == null)
            throw new NullPointerException("Invalid party in the given payment");

        party.addPayment(payment);

        List<Transaction> transactions = party.getTransactions();

        if(transactions == null) return;

        Collections.sort(transactions);

        for (Transaction transaction : transactions) {
            adjustPayment(transaction, payment);

            if(payment.getLeftOver() <= 0) break;
        }


    }
}
