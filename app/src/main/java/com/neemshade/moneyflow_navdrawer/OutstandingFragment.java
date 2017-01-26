package com.neemshade.moneyflow_navdrawer;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SpinnerAdapter;

import com.neemshade.moneyflow_navdrawer.data.Company;
import com.neemshade.moneyflow_navdrawer.data.Party;
import com.neemshade.moneyflow_navdrawer.services.OutstandingAdapterGenerator;

import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class OutstandingFragment extends Fragment {


    public OutstandingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_outstanding, container, false);
        purchaseData(view);
        saleData(view);
        return view;
    }





    public void purchaseData(View v)
    {
        OutstandingAdapterGenerator adapterGenerator_purchase = new OutstandingAdapterGenerator();
        adapterGenerator_purchase.setContext(getActivity());

        ListView listView = (ListView)v.findViewById(R.id.outstandingPurchaseList) ;


        adapterGenerator_purchase.setListViewResource(R.layout.list_item);
        adapterGenerator_purchase.setPurchase(true);
        adapterGenerator_purchase.setCompany(Company.getSelectedCompany());
        adapterGenerator_purchase.setViewIds(new int[]{R.id.textViewPartyName,R.id.textViewTotal});
        SimpleAdapter listAdapter = adapterGenerator_purchase.generateAdapter();
        listView.setAdapter(listAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Log.d("Tag",adapterView.getSelectedItem()+ "");
                Adapter adapter = adapterView.getAdapter();
                Log.d("data ", adapter.getItem(i) + "");

                Map<String, String> selectedMap = (Map<String, String>) adapter.getItem(i);
                String selectedPartyName = selectedMap.get(OutstandingAdapterGenerator.HEADER_PARTY);
                Party selectedParty = null;

                if(selectedMap != null && selectedPartyName != null)
                {
                    Company selectedCompany = Company.getSelectedCompany();
                    List<Party> parties = selectedCompany.getParties();

                    for (Party party : parties ) {
                        if(selectedPartyName.equals(party.getName().displayName()))
                        {
                            selectedParty = party;
                            break;
                        }
                    }
                }

                if(selectedParty != null) {
//                    Log.d("selected name", selectedParty.getName().displayName());

                    Fragment fragment = new PendingFragment();
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
//                    Bundle args = new Bundle();
//                    args.putString("partyName", selectedParty.getName().displayName().toString());
//                    fragment.setArguments(args);
                    ft.replace(R.id.content_frame,fragment);
                    ft.commit();



//                    Fragment fragment1;
//                    fragment1 = new PendingFragment();
//                    FragmentTransaction ft1 = getFragmentManager().beginTransaction();
//                    ft1.replace(R.id.content_frame, fragment1);
//                    ft1.commit();
                }
            }
        });

    }



    public void saleData(View v)
    {
        OutstandingAdapterGenerator adapterGenerator_sale = new OutstandingAdapterGenerator();
        adapterGenerator_sale.setContext(getActivity());

        ListView listView = (ListView)v.findViewById(R.id.outstandingSaleList) ;


        adapterGenerator_sale.setListViewResource(R.layout.list_item);
        adapterGenerator_sale.setPurchase(false);
        adapterGenerator_sale.setCompany(Company.getSelectedCompany());
        adapterGenerator_sale.setViewIds(new int[]{R.id.textViewPartyName,R.id.textViewTotal});
        SimpleAdapter listAdapter = adapterGenerator_sale.generateAdapter();
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Fragment fragment = new PendingFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.content_frame,fragment);
                ft.commit();
            }
        });

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);



        if(isVisibleToUser)
        {

        }
    }
}
