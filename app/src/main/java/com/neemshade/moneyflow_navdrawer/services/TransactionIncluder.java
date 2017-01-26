package com.neemshade.moneyflow_navdrawer.services;

import com.neemshade.moneyflow_navdrawer.data.Party;
import com.neemshade.moneyflow_navdrawer.data.Payment;
import com.neemshade.moneyflow_navdrawer.data.Transaction;

import java.util.Collections;
import java.util.List;

/**
 * Created by Balaji on 21-12-2016.
 */

public class TransactionIncluder extends EntityIncluder {

    public void addTransaction(Transaction transaction)
    {
        Party party = transaction.getParty();
        if(party == null)
            throw new NullPointerException("Invalid party in the given transaction");

        party.addTransaction(transaction);

        List<Payment> payments = party.getPayments();

        if(payments == null) return;

        Collections.sort(payments);

        for (Payment payment : payments) {
            adjustPayment(transaction, payment);

            if(transaction.getLeftOver() <= 0) break;
        }


    }

}
