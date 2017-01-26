package com.neemshade.moneyflow_navdrawer.services;

import com.neemshade.moneyflow_navdrawer.data.Party;
import com.neemshade.moneyflow_navdrawer.data.Transaction;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import com.neemshade.moneyflow.phase1.screens.R;

/**
 * Created by Balaji on 20-12-2016.
 */

public class OutstandingAdapterGenerator extends AdapterGenerator {

    public static final String HEADER_PARTY = "party";
    public static final String HEADER_TOTAL = "total";

    public OutstandingAdapterGenerator() {
        columnHeaders = new String[] { HEADER_PARTY, HEADER_TOTAL};
//        viewIds = new int[] {R.id.textViewPartyName, R.id.textViewTotal };
    }

    @Override
    protected List<List<String>> composeDataInListOfList() {
        List<List<String>> data = new ArrayList<List<String>>();
        Map<String, Float> partyMap = new HashMap<String, Float>();

        if(company == null || company.getParties() == null) return data;

        for (Party party : company.getParties()) {
            for (Transaction transaction : party.getTransactions()) {
                if(!isConsiderable(transaction)) continue;

                String displayName = party.getName().displayName();
                partyMap.put(displayName,
                        (partyMap.containsKey(displayName) ? partyMap.get(displayName) : 0)
                        + transaction.getLeftOver());

            }
        }

        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        Float total = 0f;
        for (String partyName : partyMap.keySet()) {
            List<String> row = new ArrayList<String>();

            row.add(partyName);
            row.add( df.format(partyMap.get(partyName)) + "");
            data.add(row);

            total += partyMap.get(partyName);
        }

        List<String> row = new ArrayList<String>();

        row.add("Total");
        row.add( df.format(total) + "");
        data.add(row);

        return data;
    }


}
