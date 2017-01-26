package com.neemshade.moneyflow_navdrawer.services;

import com.neemshade.moneyflow_navdrawer.data.Party;
import com.neemshade.moneyflow_navdrawer.data.Transaction;

import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Balaji on 20-12-2016.
 */

public class PendingAdapterGenerator extends AdapterGenerator {

    public static final String HEADER_PARTY = "party";
    public static final String HEADER_AMOUNT = "amount";
    public static final String HEADER_DUE_DATE = "due date";


    private Party selectedParty = null;
    private boolean isAllTrans = false;  // should we list out all transactions or only the pending transactions

    public PendingAdapterGenerator() {
        columnHeaders = new String[] { HEADER_PARTY, HEADER_AMOUNT, HEADER_DUE_DATE};
//        viewIds = new int[] {R.id.textViewPartyName, R.id.textViewAmount, R.id.textViewDueDate };
    }

    @Override
    protected List<List<String>> composeDataInListOfList() {
        List<List<String>> data = new ArrayList<List<String>>();


        if(company == null || company.getParties() == null) return data;

        List<Transaction> allTransactions = new ArrayList<Transaction>();


        for (Party party : company.getParties()) {
            allTransactions.addAll(party.getTransactions());
        }

        Collections.sort(allTransactions);

        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context);
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        Float total = 0f;
            for (Transaction transaction : allTransactions) {
                if(!isConsiderable(transaction)) continue;

                String displayName = transaction.getParty().getName().displayName();

                List<String> row = new ArrayList<String>();
                row.add(displayName);
                row.add(df.format(transaction.getLeftOver()) + "");
                row.add(dateFormat.format(transaction.getDueDate()) + "");
                data.add(row);

                total += transaction.getLeftOver();
            }

        List<String> row = new ArrayList<String>();

        row.add("Total");
        row.add( df.format(total) + "");
        row.add("");
        data.add(row);


        return data;
    }

    @Override
    protected boolean isConsiderable(Transaction transaction) {
        if(!super.isConsiderable(transaction)) return false;

        if((!isAllTrans) && transaction.getDueDate().compareTo(new Date()) > 0) return false;

        if(selectedParty == null || "All".equals(selectedParty.getName().getName()))
            return true;

        if(selectedParty.getName().getName().equals(transaction.getParty().getName().getName()))
            return true;

        return false;
    }

    public boolean isAllTrans() {
        return isAllTrans;
    }

    public void setAllTrans(boolean allTrans) {
        isAllTrans = allTrans;
    }


    public Party getSelectedParty() {
        return selectedParty;
    }

    public void setSelectedParty(Party selectedParty) {
        this.selectedParty = selectedParty;
    }
}
