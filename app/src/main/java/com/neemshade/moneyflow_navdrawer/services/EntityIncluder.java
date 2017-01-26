package com.neemshade.moneyflow_navdrawer.services;

import com.neemshade.moneyflow_navdrawer.data.Payment;
import com.neemshade.moneyflow_navdrawer.data.Transaction;

/**
 * Helper class that include transaction or payment into the party
 * Goes through existing transactions and payment to update leftOver
 * Created by Balaji on 21-12-2016.
 */

public abstract class EntityIncluder {

    /**
     * deduct leftOver from trans and payment
     *
     * @param transaction
     * @param payment
     */
    protected void adjustPayment(Transaction transaction, Payment payment) {
        if(transaction == null || payment == null) return;
        if(transaction.getLeftOver() == 0 || payment.getLeftOver() == 0) return;

        if(transaction.getLeftOver() == payment.getLeftOver())
        {
            transaction.setLeftOver(0);
            payment.setLeftOver(0);
            return;
        }

        if(transaction.getLeftOver() < payment.getLeftOver())
        {
            payment.setLeftOver(payment.getLeftOver() - transaction.getLeftOver());
            transaction.setLeftOver(0);
            return;
        }

        if(transaction.getLeftOver() > payment.getLeftOver())
        {
            transaction.setLeftOver(transaction.getLeftOver() - payment.getLeftOver());
            payment.setLeftOver(0);
            return;
        }
    }
}
