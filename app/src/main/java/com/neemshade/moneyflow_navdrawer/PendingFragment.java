package com.neemshade.moneyflow_navdrawer;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Switch;

import com.neemshade.moneyflow_navdrawer.data.Company;
import com.neemshade.moneyflow_navdrawer.data.Party;
import com.neemshade.moneyflow_navdrawer.services.PendingAdapterGenerator;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PendingFragment extends Fragment {

boolean transactionSwitchValue=true;


    public PendingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate(R.layout.fragment_pending, container, false);


        final Spinner partySpinner = (Spinner) view.findViewById(R.id.partyListSpinner_pending) ;
        Switch transactionSwitch = (Switch) view.findViewById(R.id.transSwitch);

        Company selectedCompany = Company.getSelectedCompany();
        List<Party> parties = selectedCompany.getParties();



        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item);

        adapter.add("All");
        for (Party party : parties) {
            adapter.add(party.getName().getName());
        }

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        partySpinner.setAdapter(adapter);



        partySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                purchaseData(getView());
                saleData(getView());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.d("spinnerItem", "nothing select");
            }
        });


        transactionSwitch.setChecked(transactionSwitchValue);
        transactionSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {

                purchaseData(getView());
                saleData(getView());
            }
        });


        purchaseData(view);
        saleData(view);

        return view;
    }


    public void purchaseData(View v)
    {
        PendingAdapterGenerator adapterGenerator_pending = new PendingAdapterGenerator();
        adapterGenerator_pending.setContext(getActivity());

        Spinner partySpinner = (Spinner) v.findViewById(R.id.partyListSpinner_pending) ;
        Log.d("test1", partySpinner.toString());
        Log.d("test2", partySpinner.getSelectedItem().toString());
        Party selectedParty = Party.findParty(Company.getSelectedCompany().getParties(), partySpinner.getSelectedItem().toString());
        adapterGenerator_pending.setSelectedParty(selectedParty);

        Switch transactionSwitch = (Switch) v.findViewById(R.id.transSwitch);
        adapterGenerator_pending.setAllTrans(transactionSwitch.isChecked());


        ListView listView = (ListView)v.findViewById(R.id.pendingPurchaseList) ;


        adapterGenerator_pending.setListViewResource(R.layout.list_item);
        adapterGenerator_pending.setPurchase(true);
        adapterGenerator_pending.setCompany(Company.getSelectedCompany());

        adapterGenerator_pending.setViewIds(new int[]{R.id.textViewPartyName,R.id.textViewTotal, R.id.dueDate});
        SimpleAdapter listAdapter = adapterGenerator_pending.generateAdapter();
        listView.setAdapter(listAdapter);


    }

    public void saleData(View v){



        PendingAdapterGenerator adapterGenerator_pending = new PendingAdapterGenerator();
        adapterGenerator_pending.setContext(getActivity());

        Spinner partySpinner = (Spinner) v.findViewById(R.id.partyListSpinner_pending) ;
        Party selectedParty = Party.findParty(Company.getSelectedCompany().getParties(), partySpinner.getSelectedItem().toString());
        adapterGenerator_pending.setSelectedParty(selectedParty);

        Switch transactionSwitch = (Switch) v.findViewById(R.id.transSwitch);
        adapterGenerator_pending.setAllTrans(transactionSwitch.isChecked());


        ListView listView = (ListView)v.findViewById(R.id.pendingSaleList) ;


        adapterGenerator_pending.setListViewResource(R.layout.list_item);
        adapterGenerator_pending.setPurchase(false);
        adapterGenerator_pending.setCompany(Company.getSelectedCompany());

        adapterGenerator_pending.setViewIds(new int[]{R.id.textViewPartyName,R.id.textViewTotal, R.id.dueDate});
        SimpleAdapter listAdapter = adapterGenerator_pending.generateAdapter();
        listView.setAdapter(listAdapter);
    }


}
